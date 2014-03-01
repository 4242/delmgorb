package com.organization4242.delmgorb.model;

import java.util.Arrays;

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

    public void setxVal(double[] newXVal) {
        if (newXVal == null) {
            this.xVal = new double[0];
        } else {
            this.xVal = Arrays.copyOf(newXVal, newXVal.length);
        }
    }

    public void setyVal(double[] newYVal) {
        if (newYVal == null) {
            this.yVal = new double[0];
        } else {
            this.yVal = Arrays.copyOf(newYVal, newYVal.length);
        }
    }

    PointsArray(int a, int b) {
        this.xVal = new double[a];
        this.yVal = new double[b];
        this.fVal = new double[a][b];
    }
}
