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
    private MainWindowView view;
    private PlotView plotView;

    public PlotBuilder() {

    }

    public PlotView build(MainWindowView view, MainWindowModel mainWindowModel, DataModel dataModel,
                          Boolean calculateFromScratch) {
        this.view = view;
        this.dataModel = dataModel;
        this.dataModel.addObserver(this);
        Points points;
        if (calculateFromScratch) {
            points = dataModel.buildPoints(mainWindowModel.getNumberOfPoints(), mainWindowModel.getAngle(),
                    mainWindowModel.getTimePeriod(), mainWindowModel.getTimeStep(),
                mainWindowModel.getPhi(), mainWindowModel.getTheta(), mainWindowModel.getPsi(), mainWindowModel.getIntegrationMethod(),
                mainWindowModel.getxMin(), mainWindowModel.getxMax(), mainWindowModel.getyMin(), mainWindowModel.getyMax());
        } else {
            points = dataModel.getPoints();
        }

        MultivariateFunction function = interpolatorModel.interpolate(points, mainWindowModel.getInterpolationMethod(), mainWindowModel.getNumberOfSpheres());
        PlotModel plotModel = new PlotModel(function, mainWindowModel.getxMin(), mainWindowModel.getxMax(), mainWindowModel.getyMin(), mainWindowModel.getyMax());
        plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> " + mainWindowModel.getAngle());
        plotView.setModel(plotModel);
        plotView.getTask().execute();
        showDialog();
        view.setEnabled(false);
        plotView.display();
        dataModel.setPoints(points);
        view.setEnabled(true);
        return plotView;
    }

    private void showDialog() {
        DialogWindowView dialogWindowView;
        dialogWindowView = new DialogWindowView(view, "Drawing...", false);
        dialogWindowView.display();
        while (!plotView.getTask().isDone()) {
            dialogWindowView.getProgressBar().setValue(plotView.getTask().getProgress());
        }
        dialogWindowView.dispose();
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
