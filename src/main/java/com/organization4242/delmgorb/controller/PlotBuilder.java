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

    public PlotView build(MainWindowView view, MainWindowModel model, Boolean calculateFromScratch) {
        PointsArray pointsArray;
        if (calculateFromScratch) {
            pointsArray = dataModel.buildPoints(model.getNumberOfPoints(), model.getAngle(), model.getTimePeriod(), model.getTimeStep(),
                model.getPhi(), model.getTheta(), model.getPsi(), model.getIntegrationMethod(),
                model.getxMin(), model.getxMax(), model.getyMin(), model.getyMax());
        } else {
            pointsArray = model.getPointsArray();
        }

        MultivariateFunction function = interpolatorModel.interpolate(pointsArray, model.getInterpolationMethod(), model.getNumberOfSpheres());
        PlotModel plotModel = new PlotModel(function, model.getxMin(), model.getxMax(), model.getyMin(), model.getyMax());
        PlotView plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> " + model.getAngle());
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
        model.setPointsArray(pointsArray);
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
