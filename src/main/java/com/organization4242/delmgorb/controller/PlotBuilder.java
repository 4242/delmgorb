package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.view.PlotView;
import com.organization4242.delmgorb.view.PlotWindowView;
import org.apache.commons.math3.analysis.MultivariateFunction;

/**
 * Util class which represents all logic.
 *
 * @author Murzinov Ilya
 */
public class PlotBuilder {
    private InterpolatorModel interpolatorModel = new InterpolatorModel();
    private MainWindowModel mainWindowModel;
    private  DataModel dataModel;

    public void setMainWindowModel(MainWindowModel mainWindowModel) {
        this.mainWindowModel = mainWindowModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public PlotBuilder() {

    }

    public PlotWindowView build(Boolean calculateFromScratch) {
        Points points;
        if (calculateFromScratch) {
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
