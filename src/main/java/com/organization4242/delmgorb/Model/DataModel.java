package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.*;

import static java.lang.Math.*;
import static java.lang.System.out;

public class DataModel {
    private PointsArray pointsArray;

    public PointsArray getPointsArray() {
        return pointsArray;
    }

    public DataModel(int numberOfPoints, BuildingAngle buildingAngle,
                     double timePeriod, double timeStep, IntegrationMethods method,
                     double xMin, double xMax, double yMin, double yMax) {
        //Here listOfPoints gets assigned
        double angle = PI/20;
        pointsArray = buildNewPoints(numberOfPoints, buildingAngle, timePeriod, timeStep,
                angle, angle, angle, method, xMin, xMax, yMin, yMax);
    }

    private PointsArray buildNewPoints (int numOfPoints, BuildingAngle buildingAngle, double time, double timeStep,
                                        double phi0, double theta0, double psi0, IntegrationMethods method,
                                        double xMin, double xMax, double yMin, double yMax) {
        out.println("Inside buildNewPoints");
        PointsArray comboArray;
        comboArray = new PointsArray(numOfPoints, numOfPoints);
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

        /*вычисляем начальные условия. на входе они в самолетных углах, а нужны в кватернионах*/

        double sPh0 = sin(phi0 / 2);
        double sPs0 = sin(psi0 / 2);
        double sTh0 = sin(theta0 / 2);
        double cPh0 = cos(phi0 / 2);
        double cPs0 = cos(psi0 / 2);
        double cTh0 = cos(theta0 / 2);

        double lambda0 = cPh0*cPs0*cTh0 + sPh0*sPs0*sTh0;
        double lambda1 = sPh0*cPs0*cTh0 - cPh0*sPs0*sTh0;
        double lambda2 = cPh0*cPs0*sTh0 + sPh0*sPs0*cTh0;
        double lambda3 = cPh0*sPs0*cTh0 - sPh0*cPs0*sTh0;

        /*строим разбиение треугольника Белецкого (по сути плоскость параметров эпсилон-дельта) на точки*/

        double eps = 0;
        double del = 0;
        for (int i = 0; i < numOfPoints; i++) {
            eps = yMin + 1.0 * i * (yMax - yMin) / (numOfPoints - 1);
            comboArray.y_val[i] = eps;
            System.out.println("y_val = " + comboArray.y_val[i]);
            }
        for (int j = 0; j < numOfPoints; j++) {
            del = xMin + 1.0 * j * (xMax - xMin) / (numOfPoints - 1);
            comboArray.x_val[j] = del;
            System.out.println("x_val = " + comboArray.x_val[j]);
        }
        out.print(comboArray.x_val[0]);out.print(" ");out.println(comboArray.x_val[numOfPoints - 1]);
        out.print(comboArray.y_val[0]);out.print(" ");out.println(comboArray.y_val[numOfPoints-1]);
        for (int i = 0; i < numOfPoints; i++) {
            for (int j = 0; j < numOfPoints; j++) {
                double max;
                max = 0;
                for (int t = 1; t <= time/timeStep; t++) {
                    double[] y0; // initial state
                    y0 = new double[] { lambda0, lambda1, lambda2, lambda3, 0, 1, 0 };
                    double[] y1; // final state
                    y1 = new double[] { 0, 0, 0, 0, 0, 0, 0 };
                    double time_state;
                    time_state = 1.0*t*timeStep;
                    FirstOrderDifferentialEquations ode = new LibrationODE(1000, comboArray.y_val[i],
                            comboArray.x_val[j], 0.001078011072);
                    integrator.integrate(ode, 0.0, y0, time_state, y1);// now y1 contains final state at time t/100
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
                    if (angleToPlot >= max) max = angleToPlot;
                }
                comboArray.f_val[j][i] = max;
                out.print("eps = ");out.print(comboArray.y_val[i]);
                out.print(" del = ");out.print(comboArray.x_val[j]);
                out.print(" val = ");out.println(comboArray.f_val[j][i]);
            }
        }
        out.println("Outside buildNewPoints");
        return comboArray;
    }
}
