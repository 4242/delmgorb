package com.organization4242.delmgorb.view;

import com.organization4242.delmgorb.model.PlotModel;
import net.ericaro.surfaceplotter.JSurfacePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class represents a panel with plot.
 * It is meant to be placed inside {@link view.PlotWindowView}.
 * 
 * @author Murzinov Ilya
 */
public class PlotView extends JPanel {
    private JSurfacePanel surfacePanel;
    private SwingWorker task;

    /**
    * Returns SwingWorker which represents actual plotting.
    * You should execute this task manually when you want to.
    */
    public SwingWorker getTask() {
        return task;
    }

    public PlotView() {
        surfacePanel = new JSurfacePanel();
    }

    /**
    * Sets PlotModel with actual data.
    */
    public void setModel(PlotModel model) {
        surfacePanel.setModel(model.getModel());
        setLayout(new BorderLayout());
        task = model.getModel().plot();
    }
    
    /**
    * Sets title of panel.
    */
    public void setTitleText(String text) {
        surfacePanel.setTitleText(text);
    }

    /**
    * Shows the panel with plot.
    */
    public void display() {
        surfacePanel.setBackground(Color.white);
        surfacePanel.setConfigurationVisible(true);
        add(surfacePanel, BorderLayout.CENTER);
    }
}
