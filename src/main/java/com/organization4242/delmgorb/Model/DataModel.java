package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.*;

import java.util.Observable;

import static java.lang.Math.*;

public class DataModel extends Observable {
    private Boolean stop = false;

    public void Stop() {
        stop = true;
    }

    public DataModel() {

    }

    public PointsArray buildPoints(int numberOfPoints, BuildingAngle buildingAngle,
                     double timePeriod, double timeStep, double phi0, double theta0, double psi0,
                     IntegrationMethods method, double xMin, double xMax, double yMin, double yMax) {
        return buildNewPoints(numberOfPoints, buildingAngle, timePeriod, timeStep,
                phi0, theta0, psi0, method, xMin, xMax, yMin, yMax);
    }

    private FirstOrderIntegrator GetIntegrationMethod (IntegrationMethods method){
        FirstOrderIntegrator integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        switch (method) {
            case Euler: integrator = new EulerIntegrator(0.01); break;
            case Midpoint: integrator = new MidpointIntegrator(0.05); break;
            case ClassicalRungeKutta: integrator = new ClassicalRungeKuttaIntegrator(0.1); break;
            case Gill: integrator = new GillIntegrator(0.1); break;
            case ThreeEights: integrator = new ThreeEighthesIntegrator(0.05); break;
            case HighamAndHall: integrator = new HighamHall54Integrator(0.05, 0.1, 1.0, 0.5);break;
            case DormandPrince5: integrator = new DormandPrince54Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);break;
            case DormandPrince8: integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case GraggBulirschStoer: integrator = new GraggBulirschStoerIntegrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case AdamsBashforth: integrator = new AdamsBashforthIntegrator(3, 0.01, 0.05, 1.0, 0.5); break;
            case AdamsMoulton: integrator = new AdamsMoultonIntegrator(2, 0.01, 0.05, 1.0, 0.5); break;
        }
        return integrator;
    }

    private double[] GetInitialVector (double phi0, double theta0, double psi0){
        /*вычисляем начальные условия. на входе они в самолетных углах, а нужны в кватернионах*/
        double[] y0; // initial state
        double sPh0 = sin((phi0 * PI) / 2);
        double sPs0 = sin((psi0 * PI) / 2);
        double sTh0 = sin((theta0 * PI) / 2);
        double cPh0 = cos((phi0 * PI) / 2);
        double cPs0 = cos((psi0 * PI) / 2);
        double cTh0 = cos((theta0 * PI) / 2);

        double lambda0 = cPh0*cPs0*cTh0 + sPh0*sPs0*sTh0;
        double lambda1 = sPh0*cPs0*cTh0 - cPh0*sPs0*sTh0;
        double lambda2 = cPh0*cPs0*sTh0 + sPh0*sPs0*cTh0;
        double lambda3 = cPh0*sPs0*cTh0 - sPh0*cPs0*sTh0;
        y0 = new double[] { lambda0, lambda1, lambda2, lambda3, 0, 1, 0 };
        return y0;
    }

    private double[] DoFragmentation (double aMin, double aMax, int points){
        double[] array = new double[points];
        double eps = 0;
        for (int i = 0; i < points; i++) {
            eps = aMin + 1.0 * i * (aMax - aMin) / (points - 1);
            array[i] = eps;
        }
        return array;
    }

    private double GetAngleToPlot (double[] y1, BuildingAngle buildingAngle){
        double angleToPlot = 0;
        switch (buildingAngle) {
            case Psi: {
                double alpha_1  = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];
                double beta_1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);
                angleToPlot = atan(beta_1 / alpha_1);
                break;
            }
            case Phi: {
                double gamma_2 = 2*(y1[2]*y1[3] + y1[0]*y1[1]);
                double gamma_3 = y1[0]*y1[0] - y1[1]*y1[1] - y1[2]*y1[2] + y1[3]*y1[3];
                angleToPlot = atan(gamma_2/gamma_3);
                break;
            }
            case Theta: {
                double gamma_1 = 2*(y1[1]*y1[3] - y1[0]*y1[2]);
                angleToPlot = -asin(gamma_1);
                break;
            }
        }
        return angleToPlot;
    }

    private double GetMaxValue (BuildingAngle buildingAngle,
                                double time, double timeStep, double epsilon, double delta,
                                FirstOrderIntegrator integrator, double[] initialState) {
        double max = 0;
        for (int t = 1; t <= time/timeStep; t++) {
            double[] finalState;
            finalState = new double[] { 0, 0, 0, 0, 0, 0, 0 };
            double time_state;
            time_state = 1.0*t*timeStep;
            FirstOrderDifferentialEquations ode = new LibrationODE(1000, epsilon, delta, 0.001078011072);
            integrator.integrate(ode, 0.0, initialState, time_state, finalState);
            double angleToPlot = GetAngleToPlot(finalState, buildingAngle);
            if (angleToPlot >= max) max = angleToPlot;
        }
        return max;
    }

    private PointsArray buildNewPoints (int numOfPoints, BuildingAngle buildingAngle,
                                        double time, double timeStep, double phi0, double theta0, double psi0,
                                        IntegrationMethods method, double xMin, double xMax, double yMin, double yMax) {
        PointsArray comboArray;
        comboArray = new PointsArray(numOfPoints, numOfPoints);
        FirstOrderIntegrator integrator = GetIntegrationMethod(method);
        comboArray.x_val = new double[numOfPoints];
        comboArray.y_val = new double[numOfPoints];
        comboArray.x_val = DoFragmentation(xMin, xMax, numOfPoints);
        comboArray.y_val = DoFragmentation(yMin, yMax, numOfPoints);
        double[] initialState = GetInitialVector(phi0, psi0, theta0);
        for (int i = 0; i < numOfPoints; i++) {
            for (int j = 0; j < numOfPoints; j++) {
                comboArray.f_val[j][i] = GetMaxValue(buildingAngle, time, timeStep,
                        comboArray.y_val[i], comboArray.x_val[j], integrator, initialState);;
                if (stop)
                    return null;
                //Notifying progress bar
                setChanged();
                notifyObservers((int) (((double) (i*numOfPoints + j + 1)/Math.pow(numOfPoints,2))*100));
            }
        }
        setChanged();
        notifyObservers("calculated");
        return comboArray;
    }
}
