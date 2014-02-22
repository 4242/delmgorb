package com.organization4242.delmgorb.View;

import com.organization4242.delmgorb.Model.GraphModel;
import net.ericaro.surfaceplotter.JSurfacePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class GraphView extends JPanel{
    private GraphModel model;

    public GraphView(GraphModel model) {
        this.model = model;
        initComponents();
    }

    private void initComponents() {
        JSurfacePanel surfacePanel = new JSurfacePanel();
        setLayout(new BorderLayout());
        surfacePanel.setModel(model.getModel());
        model.getModel().plot().execute();
        surfacePanel.setTitleText("title");
        surfacePanel.setBackground(Color.white);
        surfacePanel.setConfigurationVisible(true);
        add(surfacePanel, BorderLayout.CENTER);
    }
}
