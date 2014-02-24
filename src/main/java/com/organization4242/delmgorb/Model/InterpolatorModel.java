package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator;

import java.util.ArrayList;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public class InterpolatorModel {
    MicrosphereInterpolator interpolator = new MicrosphereInterpolator();

    public MultivariateFunction getFunction(ArrayList<Point3D> list) {
        double[][] xy = new double[list.size()][2];
        double[] z = new double[list.size()];

        for (int i = 0; i<list.size(); i++) {
            xy[i][0] = list.get(i).x;
            xy[i][1] = list.get(i).y;
            z[i] = list.get(i).z;
        }

        return interpolator.interpolate(xy, z);
    }
}
