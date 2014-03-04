package com.organization4242.delmgorb.model;

import java.util.Arrays;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class Points {
    private Double[] xVal;
    private Double[] yVal;
    private Double[][] fVal;

    public Double[] getxVal() {
        return xVal;
    }

    public Double[] getyVal() {
        return yVal;
    }

    public Double[][] getfVal() {
        return fVal;
    }

    public void setxVal(Double[] newXVal) {
        if (newXVal == null) {
            this.xVal = new Double[0];
        } else {
            this.xVal = Arrays.copyOf(newXVal, newXVal.length);
        }
    }

    public void setyVal(Double[] newYVal) {
        if (newYVal == null) {
            this.yVal = new Double[0];
        } else {
            this.yVal = Arrays.copyOf(newYVal, newYVal.length);
        }
    }

    Points(int a, int b) {
        this.xVal = new Double[a];
        this.yVal = new Double[b];
        this.fVal = new Double[a][b];
    }

    public Points() {

    }
}
