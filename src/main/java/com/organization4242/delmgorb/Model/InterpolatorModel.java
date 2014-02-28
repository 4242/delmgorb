package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public class InterpolatorModel {
    public MultivariateFunction interpolate(PointsArray arrays,
                                            InterpolationMethods interpolationMethods, int numberOfSpheres) {
        int length = arrays.x_val.length;
        double[][] points = new double[length*length][2];
        double[] values = new double[length*length];

        int c = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                points[c][0] = arrays.x_val[i];
                points[c][1] = arrays.y_val[j];
                values[c] = arrays.f_val[i][j];
                c++;
            }
        }


        MicrosphereInterpolator interpolator = new MicrosphereInterpolator(numberOfSpheres, 2);
        return interpolator.interpolate(points, values);
    }
}
