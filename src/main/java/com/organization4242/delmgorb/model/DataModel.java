package com.organization4242.delmgorb.model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.*;

import java.io.Serializable;
import java.util.Observable;

import static java.lang.Math.*;

public class DataModel extends Observable implements Serializable {
    private Points points;
    private MainWindowModel mainWindowModel;
    private transient Boolean stop = false;

    public Points getPoints() {
        return points;
    }

    public void setPoints(Points points) {
        this.points = points;
    }

    public void setMainWindowModel(MainWindowModel mainWindowModel) {
        this.mainWindowModel = mainWindowModel;
    }

    public void stop() {
        if (!stop) {
            stop = true;
        }
    }

    public DataModel() {

    }

    private static class IntegratorFactory {
        public static FirstOrderIntegrator createFor(IntegrationMethods method){
            FirstOrderIntegrator integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
            switch (method) {
                case EULER: integrator = new EulerIntegrator(0.01);
                    break;
                case MIDPOINT: integrator = new MidpointIntegrator(0.05);
                    break;
                case CLASSICAL_RUNGE_KUTTA: integrator = new ClassicalRungeKuttaIntegrator(0.1);
                    break;
                case GILL: integrator = new GillIntegrator(0.1);
                    break;
                case THREE_EIGHTS: integrator = new ThreeEighthesIntegrator(0.05);
                    break;
                case HIGHAM_AND_HALL: integrator = new HighamHall54Integrator(0.05, 0.1, 1.0, 0.5);
                    break;
                case DORMAND_PRINCE_5: integrator = new DormandPrince54Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
                    break;
                case DORMAND_PRINCE_8: integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
                    break;
                case GRAGG_BULIRSCH_STOER: integrator = new GraggBulirschStoerIntegrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
                    break;
                case ADAMS_BASHFORTH: integrator = new AdamsBashforthIntegrator(3, 0.01, 0.05, 1.0, 0.5);
                    break;
                case ADAMS_MOULTON: integrator = new AdamsMoultonIntegrator(2, 0.01, 0.05, 1.0, 0.5);
                    break;
            }
            return integrator;
        }
    }

    private static class InitialConditionsFactory {
        public static double[] createConditions(double phi0, double theta0, double psi0){
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
    }

    private Double[] doFragmentation(double aMin, double aMax, int points){
        Double[] array = new Double[points];
        double eps = 0;
        for (int i = 0; i < points; i++) {
            eps = aMin + 1.0 * i * (aMax - aMin) / (points - 1);
            array[i] = eps;
        }
        return array;
    }

    private static class AngleFactory {
        public static double create(double[] y1, Angle angle){
            double angleToPlot = 0;
            switch (angle) {
                case PSI: {
                    double alpha1  = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];
                    double beta1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);
                    angleToPlot = Math.abs(atan(beta1 / alpha1));
                    break;
                }
                case PHI: {
                    double gamma2 = 2*(y1[2]*y1[3] + y1[0]*y1[1]);
                    double gamma3 = y1[0]*y1[0] - y1[1]*y1[1] - y1[2]*y1[2] + y1[3]*y1[3];
                    angleToPlot = Math.abs(atan(gamma2 / gamma3));
                    break;
                }
                case THETA: {
                    double gamma1 = 2*(y1[1]*y1[3] - y1[0]*y1[2]);
                    angleToPlot = Math.abs(-asin(gamma1));
                    break;
                }
                case MIN_OF_PSI_AND_ONE_THIRD_OF_PI: {
                    double alpha1  = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];
                    double beta1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);
                    angleToPlot = Math.min(Math.abs(atan(beta1 / alpha1)), 1.0471975511965976);
                    break;
                }
                case MIN_OF_PHI_AND_ONE_THIRD_OF_PI: {
                    double gamma2 = 2*(y1[2]*y1[3] + y1[0]*y1[1]);
                    double gamma3 = y1[0]*y1[0] - y1[1]*y1[1] - y1[2]*y1[2] + y1[3]*y1[3];
                    angleToPlot = Math.min(Math.abs(atan(gamma2 / gamma3)), 1.0471975511965976);
                    break;
                }
                case MIN_OF_THETA_AND_ONE_THIRD_OF_PI: {
                    double gamma1 = 2*(y1[1]*y1[3] - y1[0]*y1[2]);
                    angleToPlot = Math.min(Math.abs(-asin(gamma1)), 1.0471975511965976);
                    break;
                }
                case MIN_OF_PSI_AND_ONE_FOURTH_OF_PI: {
                    double alpha1  = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];
                    double beta1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);
                    angleToPlot = Math.min(Math.abs(atan(beta1 / alpha1)), 0.7853981633974483);
                    break;
                }
                case MIN_OF_PHI_AND_ONE_FOURTH_OF_PI: {
                    double gamma2 = 2*(y1[2]*y1[3] + y1[0]*y1[1]);
                    double gamma3 = y1[0]*y1[0] - y1[1]*y1[1] - y1[2]*y1[2] + y1[3]*y1[3];
                    angleToPlot = Math.min(Math.abs(atan(gamma2 / gamma3)), 0.7853981633974483);
                    break;
                }
                case MIN_OF_THETA_AND_ONE_FOURTH_OF_PI: {
                    double gamma1 = 2*(y1[1]*y1[3] - y1[0]*y1[2]);
                    angleToPlot = Math.min(Math.abs(-asin(gamma1)), 0.7853981633974483);
                    break;
                }
            }
            return angleToPlot;
        }
    }

    private double getMaxValue(Angle angle,
                               double time, double timeStep, double epsilon, double delta,
                               FirstOrderIntegrator integrator, double[] initialState) {
        double max = 0;
        double timeOfMax = 0;
        double t = timeStep;
        while (t <= time){
            if (stop) {
                return 0;
            }
            t+=10*timeStep;
            double[] finalState;
            finalState = new double[] { 0, 0, 0, 0, 0, 0, 0 };
            FirstOrderDifferentialEquations ode = new LibrationODE(1000, epsilon, delta, 0.001078011072);
            integrator.integrate(ode, 0.0, initialState, t, finalState);
            double angleToPlot = AngleFactory.create(finalState, angle);
            if (angleToPlot >= max) {
                max = angleToPlot;
                timeOfMax = t;
            }
        }
        t = max(timeOfMax - 20, 0.0);
        while (t <= min(time, timeOfMax + 20)){
            if (stop) {
                return 0;
            }
            t+=timeStep;
            double[] finalState;
            finalState = new double[] { 0, 0, 0, 0, 0, 0, 0 };
            FirstOrderDifferentialEquations ode = new LibrationODE(1000, epsilon, delta, 0.001078011072);
            integrator.integrate(ode, 0.0, initialState, t, finalState);
            double angleToPlot = AngleFactory.create(finalState, angle);
            if (angleToPlot >= max) {
                max = angleToPlot;
                timeOfMax = t;
            }
        }
        return max;
    }

    public void buildPoints() {
        final Points comboArray;
        comboArray = new Points(mainWindowModel.getNumberOfPoints(), mainWindowModel.getNumberOfPoints());
        //final FirstOrderIntegrator integrator = IntegratorFactory.createFor(mainWindowModel.getIntegrationMethod());
        comboArray.setXVal(doFragmentation(mainWindowModel.getXMin(), mainWindowModel.getXMax(), mainWindowModel.getNumberOfPoints()));
        comboArray.setYVal(doFragmentation(mainWindowModel.getYMin(), mainWindowModel.getYMax(), mainWindowModel.getNumberOfPoints()));
        final double[] initialState = InitialConditionsFactory.createConditions(mainWindowModel.getPhi(), mainWindowModel.getPsi(),
                mainWindowModel.getTheta());
        long timeout= System.currentTimeMillis();
        int bigNum = mainWindowModel.getNumberOfPoints();
        int midNum = bigNum/2;
        Runnable runnable1 = () -> {
            FirstOrderIntegrator integrator = IntegratorFactory.createFor(mainWindowModel.getIntegrationMethod());
            double middlePoint1 = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                    mainWindowModel.getTimeStep(), comboArray.getYVal()[mainWindowModel.getNumberOfPoints()/2],
                    comboArray.getXVal()[mainWindowModel.getNumberOfPoints()/2], integrator, initialState);
            for (int i = 0; i < mainWindowModel.getNumberOfPoints()/2; i++) {
                for (int j = 0; j < mainWindowModel.getNumberOfPoints()/2; j++) {
                    if (i>=j)
                    comboArray.getFVal()[j][i] = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                            mainWindowModel.getTimeStep(), comboArray.getYVal()[i], comboArray.getXVal()[j],
                            integrator, initialState);
                    else
                    comboArray.getFVal()[j][i] = middlePoint1;
                    if (stop) {
                        stop = false;
                        return;
                    }
                    setChanged();
                    notifyObservers((int) (((double)
                            (i * mainWindowModel.getNumberOfPoints() + j + 1) / Math.pow(mainWindowModel.getNumberOfPoints(), 2)) * 100));
                }
            }
        };
        Runnable runnable2 = () -> {
            FirstOrderIntegrator integrator = IntegratorFactory.createFor(mainWindowModel.getIntegrationMethod());
            double middlePoint2 = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                    mainWindowModel.getTimeStep(), comboArray.getYVal()[mainWindowModel.getNumberOfPoints()/2],
                    comboArray.getXVal()[mainWindowModel.getNumberOfPoints()/2], integrator, initialState);
            for (int i = mainWindowModel.getNumberOfPoints()/2; i < mainWindowModel.getNumberOfPoints(); i++) {
                for (int j = 0; j < mainWindowModel.getNumberOfPoints()/2; j++) {
                    if (i>=j)
                        comboArray.getFVal()[j][i] = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                                mainWindowModel.getTimeStep(), comboArray.getYVal()[i], comboArray.getXVal()[j],
                                integrator, initialState);
                    else
                        comboArray.getFVal()[j][i] = middlePoint2;
                    if (stop) {
                        stop = false;
                        return;
                    }
                    setChanged();
                    notifyObservers((int) (((double)
                            (i * mainWindowModel.getNumberOfPoints() + j + 1) / Math.pow(mainWindowModel.getNumberOfPoints(), 2)) * 100));
                }
            }
        };
        Runnable runnable3 = () -> {
            FirstOrderIntegrator integrator = IntegratorFactory.createFor(mainWindowModel.getIntegrationMethod());
            double middlePoint3 = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                    mainWindowModel.getTimeStep(), comboArray.getYVal()[mainWindowModel.getNumberOfPoints()/2],
                    comboArray.getXVal()[mainWindowModel.getNumberOfPoints()/2], integrator, initialState);
            for (int i = 0; i < mainWindowModel.getNumberOfPoints()/2; i++) {
                for (int j = mainWindowModel.getNumberOfPoints()/2; j < mainWindowModel.getNumberOfPoints(); j++) {
                    if (i>=j)
                        comboArray.getFVal()[j][i] = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                                mainWindowModel.getTimeStep(), comboArray.getYVal()[i], comboArray.getXVal()[j],
                                integrator, initialState);
                    else
                        comboArray.getFVal()[j][i] = middlePoint3;
                    if (stop) {
                        stop = false;
                        return;
                    }
                    setChanged();
                    notifyObservers((int) (((double)
                            (i * mainWindowModel.getNumberOfPoints() + j + 1) / Math.pow(mainWindowModel.getNumberOfPoints(), 2)) * 100));
                }
            }
        };
        Runnable runnable4 = () -> {
            FirstOrderIntegrator integrator = IntegratorFactory.createFor(mainWindowModel.getIntegrationMethod());
            double middlePoint4 = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                    mainWindowModel.getTimeStep(), comboArray.getYVal()[mainWindowModel.getNumberOfPoints()/2],
                    comboArray.getXVal()[mainWindowModel.getNumberOfPoints()/2], integrator, initialState);
            for (int i = mainWindowModel.getNumberOfPoints()/2; i < mainWindowModel.getNumberOfPoints(); i++) {
                for (int j = mainWindowModel.getNumberOfPoints()/2; j < mainWindowModel.getNumberOfPoints(); j++) {
                    if (i>=j)
                        comboArray.getFVal()[j][i] = getMaxValue(mainWindowModel.getAngle(), mainWindowModel.getTimePeriod(),
                                mainWindowModel.getTimeStep(), comboArray.getYVal()[i], comboArray.getXVal()[j],
                                integrator, initialState);
                    else
                        comboArray.getFVal()[j][i] = middlePoint4;
                    if (stop) {
                        stop = false;
                        return;
                    }
                    setChanged();
                    notifyObservers((int) (((double)
                            (i * mainWindowModel.getNumberOfPoints() + j + 1) / Math.pow(mainWindowModel.getNumberOfPoints(), 2)) * 100));
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        Thread thread4 = new Thread(runnable4);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive() || thread4.isAlive()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        timeout = System.currentTimeMillis() - timeout;
        points = comboArray;
        thread1.interrupt();
        thread2.interrupt();
        thread3.interrupt();
        thread4.interrupt();
        System.out.println("Time = " + timeout);
    }
}
