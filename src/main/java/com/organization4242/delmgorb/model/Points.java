package com.organization4242.delmgorb.model;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Data structure used to conveniently pass to
 * {@link org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator}
 *
 * @author Murzinov Ilya
 */
public class Points {
    private CopyOnWriteArrayList<Double> xVal;
    private CopyOnWriteArrayList<Double> yVal;
    private CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> fVal;

    public CopyOnWriteArrayList<Double> getXVal() {
        return xVal;
    }

    public CopyOnWriteArrayList<Double> getYVal() {
        return yVal;
    }

    public CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> getFVal() {
        return fVal;
    }

    public void setXVal(Double[] newXVal) {
        if (newXVal == null) {
            this.xVal = new CopyOnWriteArrayList<>();
        } else {
            this.xVal = new CopyOnWriteArrayList<>(Arrays.copyOf(newXVal, newXVal.length));
        }
    }

    public void setYVal(Double[] newYVal) {
        if (newYVal == null) {
            this.yVal = new CopyOnWriteArrayList<>();
        } else {
            this.yVal = new CopyOnWriteArrayList<>(Arrays.copyOf(newYVal, newYVal.length));
        }
    }

    Points(int a) {
        this.xVal = new CopyOnWriteArrayList<>(new Double[a]);
        this.yVal = new CopyOnWriteArrayList<>(new Double[a]);
        this.fVal = new CopyOnWriteArrayList<>();
        for (int i=0; i<a; i++) {
            this.fVal.add(new CopyOnWriteArrayList<>(new Double[a]));
        }
    }

    public Points() {

    }
}
