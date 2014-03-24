package com.organization4242.delmgorb.model;

import com.organization4242.delmgorb.controller.DialogWindowController;
import com.organization4242.delmgorb.view.PlotView;
import com.organization4242.delmgorb.view.PlotWindowView;
import org.apache.commons.math3.analysis.MultivariateFunction;
import org.springframework.beans.factory.annotation.Required;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Util class which combines all logic together.
 *
 * @author Murzinov Ilya
 */
public class PlotBuilder extends AbstractModel implements Observer {
    private InterpolatorModel interpolatorModel;
    private MainWindowModel mainWindowModel;
    private  DataModel dataModel;
    private Boolean calculateFromScratch;

    public void setInterpolatorModel(InterpolatorModel interpolatorModel) {
        this.interpolatorModel = interpolatorModel;
    }

    @Required
    public void setMainWindowModel(MainWindowModel mainWindowModel) {
        this.mainWindowModel = mainWindowModel;
    }

    @Required
    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
        dataModel.addObserver(this);
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setCalculateFromScratch(Boolean calculateFromScratch) {
        this.calculateFromScratch = calculateFromScratch;
    }

    public Task getTask() {
        return new Task();
    }

    public PlotBuilder() {

    }

    @Override
    public void viewPropertyChange(PropertyChangeEvent pce) {
        dataModel.stop();
    }

    @Override
    public void update(Observable o, Object arg) {
        firePropertyChange(DialogWindowController.PERCENTAGE, (Integer) arg - 1, arg);
    }

    private PlotWindowView build(Boolean calculateFromScratch) {
        firePropertyChange(DialogWindowController.INIT, 0, 1);
        Points points;
        dataModel.setMainWindowModel(mainWindowModel);

        if (calculateFromScratch) {
            dataModel.setPoints(null);
            dataModel.buildPoints();
        }

        points = dataModel.getPoints();

        if (points == null) {
            return null;
        }

        MultivariateFunction function = interpolatorModel.interpolate(points, mainWindowModel.getNumberOfSpheres());
        PlotModel plotModel = new PlotModel(function, mainWindowModel.getXMin(), mainWindowModel.getXMax(),
                mainWindowModel.getYMin(), mainWindowModel.getYMax());
        firePropertyChange(DialogWindowController.CALCULATED, 0, 1);
        PlotView plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> " + mainWindowModel.getAngle());
        plotView.setModel(plotModel);
        plotView.getTask().execute();
        while (!plotView.getTask().isDone()) {
            firePropertyChange(DialogWindowController.PERCENTAGE, 0, plotView.getTask().getProgress());
        }

        firePropertyChange(DialogWindowController.DISPOSE, 0, 1);

        return new PlotWindowView(plotView);
    }

    public PlotView getSample() {
        Points points;

        points = dataModel.getPoints();

        MultivariateFunction function = interpolatorModel.interpolate(points, mainWindowModel.getNumberOfSpheres());
        PlotModel plotModel = new PlotModel(function, mainWindowModel.getXMin(), mainWindowModel.getXMax(),
                mainWindowModel.getYMin(), mainWindowModel.getYMax());
        PlotView plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> " + mainWindowModel.getAngle());
        plotView.setModel(plotModel);
        plotView.getTask().execute();
        return plotView;
    }

    private class Task extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() {
            build(calculateFromScratch).display();
            return null;
        }
    }

}
