package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.view.PlotView;
import com.organization4242.delmgorb.view.PlotWindowView;
import org.apache.commons.math3.analysis.MultivariateFunction;

/**
 * Created by ilya-murzinov on 26.02.14.
 */
public class PlotBuilder {
    private InterpolatorModel interpolatorModel = new InterpolatorModel();

    public PlotBuilder() {

    }

    public PlotWindowView build(MainWindowModel mainWindowModel, DataModel dataModel,
                          Boolean calculateFromScratch) {
        Points points;
        if (calculateFromScratch) {
            dataModel.setMainWindowModel(mainWindowModel);
            dataModel.buildPoints();
            points = dataModel.getPoints();
        } else {
            points = dataModel.getPoints();
        }

        MultivariateFunction function = interpolatorModel.interpolate(points, mainWindowModel.getInterpolationMethod(),
                mainWindowModel.getNumberOfSpheres());
        PlotModel plotModel = new PlotModel(function, mainWindowModel.getxMin(), mainWindowModel.getxMax(),
                mainWindowModel.getyMin(), mainWindowModel.getyMax());
        PlotView plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> " + mainWindowModel.getAngle());
        plotView.setModel(plotModel);
        plotView.getTask().execute();
        return new PlotWindowView(plotView);
    }
}
