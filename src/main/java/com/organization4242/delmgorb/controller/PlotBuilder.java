package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.view.DialogWindowView;
import com.organization4242.delmgorb.view.MainWindowView;
import com.organization4242.delmgorb.view.PlotView;
import org.apache.commons.math3.analysis.MultivariateFunction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by ilya-murzinov on 26.02.14.
 */
public class PlotBuilder extends Observable implements Observer, PropertyChangeListener {
    private DataModel dataModel;
    private InterpolatorModel interpolatorModel = new InterpolatorModel();

    public PlotBuilder() {
        dataModel = new DataModel();
        dataModel.addObserver(this);
    }

    public PlotView build(MainWindowView view, int numberOfPoints, BuildingAngle buildingAngle, double timePeriod, double timeStep,
                                 double phi0, double theta0, double psi0, IntegrationMethods integrationMethod,
                                 double xMin, double xMax, double yMin, double yMax,
                                 InterpolationMethods interpolationMethod, int numberOfSpheres) {
        PointsArray p = dataModel.buildPoints(numberOfPoints, buildingAngle, timePeriod, timeStep,
                phi0, theta0, psi0, integrationMethod, xMin, xMax, yMin, yMax);
        MultivariateFunction function = interpolatorModel.interpolate(p, interpolationMethod, numberOfSpheres);
        PlotModel plotModel = new PlotModel(function, (float) xMin, (float) xMax, (float) yMin, (float) yMax);
        PlotView plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> " + buildingAngle + " " + integrationMethod);
        plotView.setModel(plotModel);
        plotView.getTask().execute();
        DialogWindowView dialogWindowView;
        dialogWindowView = new DialogWindowView(view, "Drawing...", false);
        dialogWindowView.setVisible(true);
        while (!plotView.getTask().isDone()) {
            dialogWindowView.getProgressBar().setValue(plotView.getTask().getProgress());
        }
        dialogWindowView.dispose();
        plotView.display();
        return plotView;
    }

    public PlotView build(MainWindowView view, PointsArray pointsArray, double xMin, double xMax, double yMin, double yMax,
                          InterpolationMethods interpolationMethod, int numberOfSpheres) {
        MultivariateFunction function = interpolatorModel.interpolate(pointsArray, interpolationMethod, numberOfSpheres);
        PlotModel plotModel = new PlotModel(function, (float) xMin, (float) xMax, (float) yMin, (float) yMax);
        PlotView plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> ");
        plotView.setModel(plotModel);
        plotView.getTask().execute();
        DialogWindowView dialogWindowView;
        dialogWindowView = new DialogWindowView(view, "Drawing...", false);
        dialogWindowView.display();
        while (!plotView.getTask().isDone()) {
            dialogWindowView.getProgressBar().setValue(plotView.getTask().getProgress());
        }
        dialogWindowView.dispose();
        plotView.display();
        return plotView;
    }
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        dataModel.stop();
    }
}
