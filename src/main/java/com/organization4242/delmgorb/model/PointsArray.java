package com.organization4242.delmgorb.model;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class PointsArray {
    private double[] xVal;
    private double[] yVal;
    private double[][] fVal;

    public double[] getxVal() {
        return xVal;
    }

    public double[] getyVal() {
        return yVal;
    }

    public double[][] getfVal() {
        return fVal;
    }

    public void setxVal(double[] xVal) {
        this.xVal = xVal;
    }

    public void setyVal(double[] yVal) {
        this.yVal = yVal;
    }

    public void setfVal(double[][] fVal) {
        this.fVal = fVal;
    }

    PointsArray(int a, int b) {
        this.xVal = new double[a];
        this.yVal = new double[b];
        this.fVal = new double[a][b];
    }
}
