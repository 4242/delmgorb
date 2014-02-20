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

    public DataModel() {
        //Here listOfPoints gets assigned
        listOfPoints = buildPoints(/*add arguments*/);
    }

    private ArrayList<Point3D> buildPoints(/*add arguments*/) {

        System.out.println("Inside buildPoints");

        ArrayList<Point3D> list = new ArrayList<Point3D>();
        FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        FirstOrderDifferentialEquations ode = new CircleODE(new double[] { 1.0, 1.0 }, 0.1);
        double[] y = new double[] { 0.0, 1.0 }; // initial state
        dp853.integrate(ode, 0.0, y, 16.0, y); // now y contains final state at time t=16.0

        Point3D point1 = new Point3D();
        Point3D point = new Point3D(1,3,4);
        point1.x = 1.2;
        point1.y = 1.3;
        point1.z = 0.2;

        list.add(0, point);
        list.add(1, point1);

        for (int i = 1; i <= 4; i++) {

            list.add(i+1,new Point3D(1,i,Math.pow(i, 2)));

        }

        System.out.println("Outside buildPoints");

        return list;
    }
}
