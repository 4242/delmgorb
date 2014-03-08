package com.organization4242.delmgorb.model;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator;

/**
 * Util class used for interpolation points from {@link com.organization4242.delmgorb.model.DataModel}
 *
 * @author Murzinov Ilya
 */
public class InterpolatorModel {
    /**
     * Produces function which is passed to {@link com.organization4242.delmgorb.view.PlotView}
     *
     * @param points set of points to be interpolated
     * @param interpolationMethods {@link com.organization4242.delmgorb.model.InterpolationMethods}
     * @param numberOfSpheres parameter of {@link org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator}
     * @return {@link org.apache.commons.math3.analysis.MultivariateFunction}
     */
    public MultivariateFunction interpolate(Points points,
                                            InterpolationMethods interpolationMethods, int numberOfSpheres) {
        int length = points.getXVal().length;
        double[][] fVals = new double[length*length][2];
        double[] values = new double[length*length];

        int c = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                fVals[c][0] = points.getXVal()[i];
                fVals[c][1] = points.getYVal()[j];
                values[c] = points.getFVal()[i][j];
                c++;
            }
        }


        MicrosphereInterpolator interpolator = new MicrosphereInterpolator(numberOfSpheres, 2);
        return interpolator.interpolate(fVals, values);
    }
}
