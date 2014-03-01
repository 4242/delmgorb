package com.organization4242.delmgorb.View;

import com.organization4242.delmgorb.Model.PlotModel;
import net.ericaro.surfaceplotter.JSurfacePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class PlotView extends JPanel {
    JSurfacePanel surfacePanel;
    SwingWorker task;

    public SwingWorker getTask() {
        return task;
    }

    public PlotView() {
        surfacePanel = new JSurfacePanel();
    }
    
    public void setModel(PlotModel model) {
        surfacePanel.setModel(model.getModel());
        setLayout(new BorderLayout());
        task = model.getModel().plot();
    }
    
    public void setTitleText(String text) {
        surfacePanel.setTitleText(text);
    }

    public void display() {
        surfacePanel.setBackground(Color.white);
        surfacePanel.setConfigurationVisible(true);
        add(surfacePanel, BorderLayout.CENTER);
    }
}
