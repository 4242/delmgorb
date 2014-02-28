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
        initComponents();
    }
    
    public void setTitleText(text) {
        surfacePanel.setTitleText(text);
    }

    private void initComponents() {
        surfacePanel = new JSurfacePanel(model.getModel());
        setLayout(new BorderLayout());
        model.getModel().plot().execute();
        surfacePanel.setBackground(Color.white);
        surfacePanel.setConfigurationVisible(true);
        add(surfacePanel, BorderLayout.CENTER);
    }
}
