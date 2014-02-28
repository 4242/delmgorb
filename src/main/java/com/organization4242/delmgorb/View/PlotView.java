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
    DialogWindowView dialogWindowView;

    public PlotView() {
        surfacePanel = new JSurfacePanel();
    }
    
    public void setModel(PlotModel model) {
        surfacePanel.setModel(model.getModel());
        setLayout(new BorderLayout());
        SwingWorker task = model.getModel().plot();
        task.execute();
        dialogWindowView = new DialogWindowView(this);
        dialogWindowView.setTitle("Drawing...");
        dialogWindowView.setVisible(true);
        while (!task.isDone()) {
            dialogWindowView.getProgressBar().setValue(task.getProgress());
        }
        dialogWindowView.dispose();
        surfacePanel.setBackground(Color.white);
        surfacePanel.setConfigurationVisible(true);
        add(surfacePanel, BorderLayout.CENTER);
    }
    
    public void setTitleText(String text) {
        surfacePanel.setTitleText(text);
    }
}
