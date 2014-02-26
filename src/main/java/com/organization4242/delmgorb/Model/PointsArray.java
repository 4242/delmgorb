package com.organization4242.delmgorb.Model;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class PointsArray {
    double[] x_val;
    double[] y_val;
    double[][] f_val;

    PointsArray(int a, int b) {
        this.x_val = new double[a];
        this.y_val = new double[b];
        this.f_val = new double[a][b];
    }
}
