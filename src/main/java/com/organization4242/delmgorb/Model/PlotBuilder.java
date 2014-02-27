package com.organization4242.delmgorb.Model;

import com.organization4242.delmgorb.View.PlotView;
import org.apache.commons.math3.analysis.MultivariateFunction;

/**
 * Created by ilya-murzinov on 26.02.14.
 */
public class PlotBuilder {
    static DataModel dataModel;
    static InterpolatorModel interpolatorModel = new InterpolatorModel();
    static PlotModel plotModel;
    static PlotView plotView;

    public static PlotView build(int numberOfPoints, BuildingAngle buildingAngle, double timePeriod, double timeStep, IntegrationMethods integrationMethod,
                                 double xMin, double xMax, double yMin, double yMax, InterpolationMethods interpolationMethod, int numberOfSpheres) {
        dataModel = new DataModel(numberOfPoints, buildingAngle, timePeriod, timeStep, integrationMethod, xMin, xMax, yMin, yMax);
        MultivariateFunction function = interpolatorModel.interpolate(dataModel.getPointsArray(), interpolationMethod, numberOfSpheres);
        plotModel = new PlotModel(function, (float) xMin, (float) xMax, (float) yMin, (float) yMax);
        plotView = new PlotView(plotModel);
        return plotView;
    }
}
