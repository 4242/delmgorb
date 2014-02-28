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

    public PlotView(PlotModel model) {
        surfacePanel = new JSurfacePanel(model.getModel());
        initComponents();
    }
    
    public void setTitleText(String text) {
        surfacePanel.setTitleText(text);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        model.getModel().plot().execute();
        surfacePanel.setBackground(Color.white);
        surfacePanel.setConfigurationVisible(true);
        add(surfacePanel, BorderLayout.CENTER);
    }
}
