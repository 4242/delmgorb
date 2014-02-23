package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
class CircleODE implements FirstOrderDifferentialEquations {
    private double A;
    private double epsilon;
    private double delta;
    private double omega_0;

    public CircleODE(double A, double epsilon, double delta, double omega_0) {
        this.A       = A;
        this.epsilon = epsilon;
        this.delta   = delta;
        this.omega_0 = omega_0;
    }

    public int getDimension() {
        return 7;
    }

    public void computeDerivatives(double t, double[] y, double[] yDot) {
        double p = (y[0]*y[0]+y[1]*y[1]+y[2]*y[2]+y[3]*y[3]-1);
        yDot[0] = -(1/2)*y[1]*y[4]-(1/2)*y[2]*y[5]-(1/2)*y[3]*y[6]+(1/2)*y[2]-(1/2)*y[0]*p;
        yDot[1] = (1/2)*y[0]*y[4]+(1/2)*y[2]*y[6]-(1/2)*y[3]*y[5]-(1/2)*y[3]-(1/2)*y[1]*p;
        yDot[2] = (1/2)*y[0]*y[5]+(1/2)*y[3]*y[4]-(1/2)*y[1]*y[6]-(1/2)*y[0]-(1/2)*y[1]*p;
        yDot[3] = (1/2)*y[0]*y[6]+(1/2)*y[1]*y[5]-(1/2)*y[2]*y[4]+(1/2)*y[1]-(1/2)*y[1]*p;
        yDot[4] = -(epsilon-delta)*(y[5]*y[6]+3*(2*y[2]*y[3]+2*y[0]*y[1])*(y[0]*y[0]-y[1]*y[1]-y[2]*y[2]+y[3]*y[3]));
        yDot[5] = -(1-epsilon)*(y[6]*y[4]/delta+3*(y[0]*y[0]-y[1]*y[1]-y[2]*y[2]+y[3]*y[3])*(2*y[1]*y[3]-2*y[0]*y[2])/delta);
        yDot[6] = -(delta-1)*(y[4]*y[5]/epsilon+3*(2*y[1]*y[3]-2*y[0]*y[2])*(2*y[2]*y[3]+2*y[0]*y[1])/epsilon);
        /*yDot[0] = 10/y[0];//здесь простые уравнения для проверки
        yDot[1] = 0;
        yDot[2] = 0;
        yDot[3] = (1/2)*y[2]*y[6];
        yDot[4] = -(epsilon-delta)*(y[5]*y[6]);
        yDot[5] = -(1-epsilon)*(y[6]*y[4]/delta);
        yDot[6] = -(delta-1)*(y[4]*y[5]/epsilon);*/

    }

}
