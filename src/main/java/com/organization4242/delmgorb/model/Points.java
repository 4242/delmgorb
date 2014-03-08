package com.organization4242.delmgorb.model;

import java.util.Arrays;

/**
 * Data structure used to conveniently pass to
 * {@link org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator}
 *
 * @author Murzinov Ilya
 */
public class Points {
    private Double[] xVal;
    private Double[] yVal;
    private Double[][] fVal;

    public Double[] getXVal() {
        return xVal;
    }

    public Double[] getYVal() {
        return yVal;
    }

    public Double[][] getFVal() {
        return fVal;
    }

    public void setXVal(Double[] newXVal) {
        if (newXVal == null) {
            this.xVal = new Double[0];
        } else {
            this.xVal = Arrays.copyOf(newXVal, newXVal.length);
        }
    }

    public void setYVal(Double[] newYVal) {
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
