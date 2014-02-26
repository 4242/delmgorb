package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator;
import static java.lang.System.out;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public class InterpolatorModel {
    public MultivariateFunction interpolate(PointsArray arrays,
                                            InterpolationMethods interpolationMethods, int numberOfSpheres) {
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
        int length = arrays.x_val.length;
        double[][] points = new double[length*length][2];
        double[] values = new double[length*length];

        out.println(" l_x= " + length);
        out.println(" l_y= " + arrays.y_val.length);

        int c = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                points[c][0] = arrays.x_val[i];
                points[c][1] = arrays.y_val[j];
                values[c] = arrays.f_val[i][j];
                out.println(" c= " + c);
                out.println(" points= " + points[c][0] + " " + points[c][1]);
                c++;
            }
        }


        MicrosphereInterpolator interpolator = new MicrosphereInterpolator(numberOfSpheres, 2);
        return interpolator.interpolate(points, values);
        //return interpolator.interpolate(new double[][]{new double[]{1,2}, new double[]{3,4}},
        //        new double[]{1,1});
    }
}
