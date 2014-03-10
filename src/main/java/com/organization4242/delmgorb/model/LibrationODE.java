package com.organization4242.delmgorb.model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class LibrationODE implements FirstOrderDifferentialEquations {
    private double a;
    private double epsilon;
    private double delta;
    private double omega0;

    public LibrationODE(double a, double epsilon, double delta, double omega0) {
        this.a = a;
        this.epsilon = epsilon;
        this.delta   = delta;
        this.omega0 = omega0;
    }

    public int getDimension() {
        return 7;
    }

    public void computeDerivatives(double t, double[] y, double[] yDot) {
        double p;
        p = (y[0]*y[0]+y[1]*y[1]+y[2]*y[2]+y[3]*y[3]-1);
        yDot[0] = -0.5*y[1]*y[4]-0.5*y[2]*y[5]-0.5*y[3]*y[6]+0.5*y[2]-0.5*y[0]*p;
        yDot[1] = 0.5*y[0]*y[4]+0.5*y[2]*y[6]-0.5*y[3]*y[5]-0.5*y[3]-0.5*y[1]*p;
        yDot[2] = 0.5*y[0]*y[5]+0.5*y[3]*y[4]-0.5*y[1]*y[6]-0.5*y[0]-0.5*y[2]*p;
        yDot[3] = 0.5*y[0]*y[6]+0.5*y[1]*y[5]-0.5*y[2]*y[4]+0.5*y[1]-0.5*y[3]*p;
        yDot[4] = (epsilon-delta)*(-y[5]*y[6]+3*(2*y[2]*y[3]+2*y[0]*y[1])*(y[0]*y[0]-y[1]*y[1]-y[2]*y[2]+y[3]*y[3]));
        yDot[5] = (1-epsilon)/delta *(-y[6]*y[4]+3*(y[0]*y[0]-y[1]*y[1]-y[2]*y[2]+y[3]*y[3])*(2*y[1]*y[3]-2*y[0]*y[2]));
        yDot[6] = (delta-1)/epsilon *(-y[4]*y[5]+3*(2*y[1]*y[3]-2*y[0]*y[2])*(2*y[2]*y[3]+2*y[0]*y[1]));
    }

}
