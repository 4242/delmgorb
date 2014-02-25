package com.organization4242.delmgorb.View;

import com.organization4242.delmgorb.Model.DataModel;
import com.organization4242.delmgorb.Model.PlotModel;
import com.organization4242.delmgorb.Model.IntegrationMethods;
import com.organization4242.delmgorb.Model.InterpolatorModel;

import javax.swing.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class PlotWindowView extends JFrame{
    private PlotView view;

    private PlotWindowView(PlotView view) {
        this.view = view;
    }

    public PlotWindowView(int numberOfPoints, double timeStep, IntegrationMethods method,
                          float xMin, float xMax, float yMin, float yMax) {
        this(new PlotView(
                new PlotModel(new InterpolatorModel()
                    .getFunction(new DataModel(numberOfPoints, timeStep, method).getThreeArrays()),
                    xMin, xMax, yMin, yMax)));
    }

    public void display() {
        JFrame jf = new JFrame("Plot");
        jf.setSize(800, 600);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.getContentPane().add(view);
        jf.setVisible(true);
    }
}
