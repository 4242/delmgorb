package com.organization4242.delmgorb.view;

import com.organization4242.delmgorb.controller.OpenFileHelper;
import com.organization4242.delmgorb.model.PlotModel;
import net.ericaro.surfaceplotter.JSurfacePanel;
import net.ericaro.surfaceplotter.surface.Projector;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Class represents a panel with plot.
 * It is meant to be placed inside {@link com.organization4242.delmgorb.view.PlotWindowView}.
 * 
 * @author Murzinov Ilya
 */
public class PlotView extends JPanel {
    private static final int DEFAULT_ANGLE_STEP = 4;

    private JSurfacePanel surfacePanel;
    private SwingWorker task;

    private Logger logger = LogManager.getLogger(PlotView.class);

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
        surfacePanel.setBackground(Color.white);
        surfacePanel.setConfigurationVisible(true);
        add(surfacePanel, BorderLayout.CENTER);

        requestFocus();

        final Projector projector = surfacePanel.getSurface().getModel().getProjector();

        surfacePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "Save");
        surfacePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
        surfacePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
        surfacePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
        surfacePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");

        surfacePanel.getActionMap().put("Save",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            File file = OpenFileHelper.open(null);
                            if (file.exists()) {
                                file = new File(file.getAbsolutePath());
                            }
                            surfacePanel.getSurface().doExportPNG(file);
                        } catch (IOException | NullPointerException ex) {
                            logger.error(ex);
                        }
                    }
                });

        surfacePanel.getActionMap().put("Up",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        float newAngle = projector.getElevationAngle() + DEFAULT_ANGLE_STEP;
                        projector.setElevationAngle(newAngle);
                        repaint();
                    }
                });

        surfacePanel.getActionMap().put("Down",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        float newAngle = projector.getElevationAngle() - DEFAULT_ANGLE_STEP;
                        projector.setElevationAngle(newAngle);
                        repaint();
                    }
                });

        surfacePanel.getActionMap().put("Right",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        float newAngle = projector.getRotationAngle() + DEFAULT_ANGLE_STEP;
                        projector.setRotationAngle(newAngle);
                        repaint();
                    }
                });

        surfacePanel.getActionMap().put("Left",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        float newAngle = projector.getRotationAngle() - DEFAULT_ANGLE_STEP;
                        projector.setRotationAngle(newAngle);
                        repaint();
                    }
                });

    }
    
    /**
    * Sets title of panel.
    */
    public void setTitleText(String text) {
        surfacePanel.setTitleText(text);
    }
}
