package com.organization4242.delmgorb.Model;

import com.organization4242.delmgorb.View.PlotView;
import org.apache.commons.math3.analysis.MultivariateFunction;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by ilya-murzinov on 26.02.14.
 */
public class PlotBuilder extends Observable implements Observer {
    DataModel dataModel;
    InterpolatorModel interpolatorModel = new InterpolatorModel();
    PlotModel plotModel;
    PlotView plotView;

    public PlotBuilder() {
        dataModel = new DataModel();
        dataModel.addObserver(this);
    }

    public PlotView build(int numberOfPoints, BuildingAngle buildingAngle, double timePeriod, double timeStep,
                                 double phi0, double theta0, double psi0, IntegrationMethods integrationMethod,
                                 double xMin, double xMax, double yMin, double yMax,
                                 InterpolationMethods interpolationMethod, int numberOfSpheres) {
        PointsArray p = dataModel.buildPoints(numberOfPoints, buildingAngle, timePeriod, timeStep,
                phi0, theta0, psi0, integrationMethod, xMin, xMax, yMin, yMax);
        MultivariateFunction function = interpolatorModel.interpolate(p, interpolationMethod, numberOfSpheres);
        plotModel = new PlotModel(function, (float) xMin, (float) xMax, (float) yMin, (float) yMax);
        plotView = new PlotView(plotModel);
        return plotView;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
