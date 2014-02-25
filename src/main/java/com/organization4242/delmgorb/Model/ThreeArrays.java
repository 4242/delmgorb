package com.organization4242.delmgorb.Model;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class ThreeArrays {
    double[] x_val;
    double[] y_val;
    double[][] f_val;

    ThreeArrays(double[] x_val, double[] y_val, double[][] f_val) {
        this.x_val = x_val;
        this.y_val = y_val;
        this.f_val = f_val;
    }

    ThreeArrays() {
    }
    @Override
    public String toString() {
        return "[" + x_val + ", " + y_val + ", " + f_val + "]";
    }
}
