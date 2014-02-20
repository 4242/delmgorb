package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

import java.util.ArrayList;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
class CircleODE implements FirstOrderDifferentialEquations {
    private double[] c;
    private double omega;

    public CircleODE(double[] c, double omega) {
        this.c     = c;
        this.omega = omega;
    }

    public int getDimension() {
        return 2;
    }

    public void computeDerivatives(double t, double[] y, double[] yDot) {
        yDot[0] = omega * (c[1] - y[1]);
        yDot[1] = omega * (y[0] - c[0]);
    }

}

public class DataModel {
    private ArrayList<Point3D> listOfPoints = new ArrayList<Point3D>();

    public ArrayList<Point3D> getListOfPoints() {
        return listOfPoints;
    }

    public DataModel(int num) {
        //Here listOfPoints gets assigned
        listOfPoints = buildPoints(num);
    }

    private ArrayList<Point3D> buildPoints(int number_of_points) {

        System.out.println("Inside buildPoints");
        ArrayList<Point3D> list = new ArrayList<Point3D>();
        FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        FirstOrderDifferentialEquations ode = new CircleODE(new double[] { 1.0, 1.0 }, 0.1);
        double[] y0 = new double[] { 0.0, 1.0 }; // initial state
        double[] y1 = new double[] { 0.0, 1.0 }; // final state
        for (int i = 0; i <= number_of_points; i++) {
            dp853.integrate(ode, 0.0, y0, i*0.01+0.01, y1);// now y1 contains final state at time i*0.01+0.01
            list.add(i,new Point3D(i,y1[0],y1[1]));
        }
        System.out.println("Outside buildPoints");
        return list;
    }
}
