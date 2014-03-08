package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.view.DialogWindowView;
import com.organization4242.delmgorb.view.PlotView;
import com.organization4242.delmgorb.view.PlotWindowView;
import org.apache.commons.math3.analysis.MultivariateFunction;
import org.springframework.beans.factory.annotation.Required;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Util class which combines all logic together.
 *
 * @author Murzinov Ilya
 */
public class PlotBuilder {
    private InterpolatorModel interpolatorModel = new InterpolatorModel();
    private MainWindowModel mainWindowModel;
    private  DataModel dataModel;
    private Task task = new Task();
    private DialogWindowView dialogWindowView;
    private Boolean calculateFromScratch;

    @Required
    public void setMainWindowModel(MainWindowModel mainWindowModel) {
        this.mainWindowModel = mainWindowModel;
    }

    @Required
    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Required
    public void setDialogWindowView(DialogWindowView dialogWindowView) {
        this.dialogWindowView = dialogWindowView;
    }

    public void setCalculateFromScratch(Boolean calculateFromScratch) {
        this.calculateFromScratch = calculateFromScratch;
    }

    public DialogWindowView getDialogWindowView() {
        return dialogWindowView;
    }

    public Task getTask() {
        task = new Task();
        return task;
    }

    public PlotBuilder() {

    }

    public PlotWindowView build(Boolean calculateFromScratch) {
        Points points;
        dataModel.addObserver(task);
        dialogWindowView.getTextArea().setText("Calculating...");
        dialogWindowView.getButton().setEnabled(true);
        dialogWindowView.getButton().addActionListener(actionListener);
        dialogWindowView.display();
        if (calculateFromScratch) {
            dataModel.buildPoints();
        }

        points = dataModel.getPoints();

        MultivariateFunction function = interpolatorModel.interpolate(points, mainWindowModel.getInterpolationMethod(),
                mainWindowModel.getNumberOfSpheres());
        PlotModel plotModel = new PlotModel(function, mainWindowModel.getXMin(), mainWindowModel.getXMax(),
                mainWindowModel.getYMin(), mainWindowModel.getYMax());
        PlotView plotView = new PlotView();
        plotView.setTitleText("X -> Delta, Y -> Epsilon, Z -> " + mainWindowModel.getAngle());
        plotView.setModel(plotModel);
        dialogWindowView.getTextArea().setText("Drawing...");
        plotView.getTask().execute();
        while (!plotView.getTask().isDone()) {
            dialogWindowView.getProgressBar().setValue(plotView.getProgress());
        }
        dialogWindowView.dispose();
        return new PlotWindowView(plotView);
    }

    private class Task extends SwingWorker<Void, Integer> implements Observer {
        @Override
        protected Void doInBackground() throws Exception {
            build(calculateFromScratch).display();
            return null;
        }

        @Override
        public void update(Observable o, Object arg) {
            dialogWindowView.getProgressBar().setValue((Integer) arg);
        }

        @Override
        protected void done() {
            dialogWindowView.getProgressBar().setValue(0);
        }
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(dialogWindowView.getButton())) {
                dataModel.stop();
                dataModel.setPoints(null);
                dialogWindowView.getProgressBar().setValue(0);
                dialogWindowView.getButton().removeActionListener(this);
                dialogWindowView.dispose();
            }
        }
    };
}
