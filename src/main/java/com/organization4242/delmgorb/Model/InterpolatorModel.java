package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.analysis.interpolation.BicubicSplineInterpolatingFunction;
import org.apache.commons.math3.analysis.interpolation.SmoothingPolynomialBicubicSplineInterpolator;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public class InterpolatorModel {
    SmoothingPolynomialBicubicSplineInterpolator interpolator = new SmoothingPolynomialBicubicSplineInterpolator();

    public BicubicSplineInterpolatingFunction getFunction(ThreeArrays arrays) {
        return interpolator.interpolate(arrays.x_val, arrays.y_val, arrays.f_val);
    }
}
