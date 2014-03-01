package com.organization4242.delmgorb.model;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public class InterpolatorModel {
    public MultivariateFunction interpolate(PointsArray arrays,
                                            InterpolationMethods interpolationMethods, int numberOfSpheres) {
        int length = arrays.getxVal().length;
        double[][] points = new double[length*length][2];
        double[] values = new double[length*length];

        int c = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                points[c][0] = arrays.getxVal()[i];
                points[c][1] = arrays.getyVal()[j];
                values[c] = arrays.getfVal()[i][j];
                c++;
            }
        }


        MicrosphereInterpolator interpolator = new MicrosphereInterpolator(numberOfSpheres, 2);
        return interpolator.interpolate(points, values);
    }
}
