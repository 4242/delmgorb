package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.analysis.interpolation.BicubicSplineInterpolatingFunction;
import org.apache.commons.math3.analysis.interpolation.BicubicSplineInterpolator;

import java.util.ArrayList;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public class InterpolatorModel {
    BicubicSplineInterpolator interpolator = new BicubicSplineInterpolator();

    public BicubicSplineInterpolatingFunction getFunction(ArrayList<Point3D> list) {
        double[] x = new double[]{-4,-3,-2,-1,1,2,3,4};
        double[] y = new double[]{-4,-3,-2,-1,1,2,3,4};
        double[][] f = new double[][]{new double[]{-4,-5,-9,-11,13,24,3,4,}, new double[]{-2,-6,-2,-1,0,2,5,4},
                new double[]{-4,-3,-2,-1,1,2,3,4}, new double[]{-4,-5,-9,-11,13,24,3,4,},
                new double[]{-4,5,-2,-11,-13,24,3,4,}, new double[]{-4,-8,-2,-5,1,8,3,4},
                new double[]{-4,-3,-21,-1,1,2,0,4}, new double[]{-2,-6,-2,-1,0,2,5,4}};

        return interpolator.interpolate(x, y, f);
    }
}
