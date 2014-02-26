package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public class InterpolatorModel {
    public MultivariateFunction interpolate(PointsArray arrays,
                                            InterpolationMethods interpolationMethods) {
//        switch (interpolationMethods) {
//            default:
//            case BicubicSpline:
//                return new BicubicSplineInterpolator()
//                        .interpolate(arrays.x_val, arrays.y_val, arrays.f_val);
//            case PolynomialSpline:
//                return new SmoothingPolynomialBicubicSplineInterpolator()
//                        .interpolate(arrays.x_val, arrays.y_val, arrays.f_val);
//            case Microsphere:
//                return new MicrosphereInterpolator().interpolate(new double[][]{new double[]{1,2}, new double[]{3,4}},
//                        new double[]{1,1});
        MicrosphereInterpolator interpolator = new MicrosphereInterpolator(100, 2);
        return interpolator.interpolate(new double[][]{new double[]{1,2}, new double[]{3,4}},
                       new double[]{1,1});
    }
}
