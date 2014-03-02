package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.application.Application;
import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.view.DialogWindowView;
import com.organization4242.delmgorb.view.MainWindowView;
import com.organization4242.delmgorb.view.PlotView;
import com.organization4242.delmgorb.view.PlotWindowView;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowController {
    private MainWindowModel model;
    private MainWindowView view;
    private Boolean canDraw = true;

    private DialogWindowView dialogWindowView;

    private PropertyChangeSupport changes;
    private PlotBuilder builder = new PlotBuilder();

    private MainWindowModelConverter mainWindowModelConverter = new MainWindowModelConverter();
    XStream xStream = new XStream(new DomDriver());

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        for (JTextField tf : view.getTextFields()) {
            tf.addFocusListener(focusListener);
        }
        for (JTextField tf : view.getBoundsTextFields()) {
            tf.addFocusListener(focusListener);
        }
        view.getImportDataMenuItem().addActionListener(menuItemActionListener);
        view.getExportDataMenuItem().addActionListener(menuItemActionListener);
        view.getButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                drawPlot();
            }
        });
        view.getNumberOfPoints().addFocusListener(focusListener);
        view.getTimeStep().addFocusListener(focusListener);
        view.getNumberOfSpheresTextField().addFocusListener(focusListener);
        view.getPeriodToInterpolate().addFocusListener(focusListener);
        view.getPhiTextField().addFocusListener(focusListener);
        view.getPsiTextField().addFocusListener(focusListener);
        view.getThetaTextField().addFocusListener(focusListener);
    }

    private class Task extends SwingWorker<Void, Integer> implements Observer {

        @Override
        protected Void doInBackground() throws Exception {
            builder.addObserver(this);
            PlotView plotView = builder.build(view, model);
            PlotWindowView plotWindowView = new PlotWindowView(plotView);
            plotWindowView.display();
            return null;
        }

        public void done() {
            view.setEnabled(true);
        }

        @Override
        public void update(Observable o, Object arg) {
            if (arg.getClass().equals(Integer.class)) {
                dialogWindowView.getProgressBar().setValue((Integer) arg);
            }
            if (arg.getClass().equals(String.class)) {
                dialogWindowView.dispose();
            }
        }
    }

    private void drawPlot() {
        canDraw = validate();

        if (canDraw) {
            if (model.getPointsArray() == null) {
                try {
                    final Task task = new Task();
                    task.execute();
                    view.setEnabled(false);
                    dialogWindowView = new DialogWindowView(view, "Calculating...", true);
                    dialogWindowView.display();
                    changes = new PropertyChangeSupport(this);
                    changes.addPropertyChangeListener(builder);
                    dialogWindowView.getButton().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            PropertyChangeEvent ev = new PropertyChangeEvent(this, "interrupt", "false", "true");
                            changes.firePropertyChange(ev);
                            dialogWindowView.dispose();
                        }
                    });
                } catch (NumberIsTooSmallException ex) {
                    JOptionPane.showMessageDialog(view, "Number of points is too small");
                }
            } else {
                PlotView plotView = builder.build(view, model);
                PlotWindowView plotWindowView = new PlotWindowView(plotView);
                plotWindowView.display();
            }
        }
    }

    private void updateModel() {
        model.setIntegrationMethod((IntegrationMethods) view.getIntegrationMethodsComboBox().getSelectedItem());
        model.setBuildingAngle((BuildingAngle) view.getBuildingAngleJComboBox().getSelectedItem());
        model.setNumberOfPoints(Integer.parseInt(view.getNumberOfPoints().getText()));
        model.setTimeStep(Double.parseDouble(view.getTimeStep().getText()));
        model.setTimePeriod(Double.parseDouble(view.getPeriodToInterpolate().getText()));
        model.setPhi0(Double.parseDouble(view.getPhiTextField().getText()));
        model.setPsi0(Double.parseDouble(view.getPsiTextField().getText()));
        model.setTheta0(Double.parseDouble(view.getThetaTextField().getText()));
        model.setxMin(Float.parseFloat(view.getBoundsTextFields()[0].getText()));
        model.setxMax(Float.parseFloat(view.getBoundsTextFields()[1].getText()));
        model.setyMin(Float.parseFloat(view.getBoundsTextFields()[2].getText()));
        model.setyMax(Float.parseFloat(view.getBoundsTextFields()[3].getText()));
        model.setNumberOfSpheres(Integer.parseInt(view.getNumberOfSpheresTextField().getText()));
        model.setInterpolationMethod(InterpolationMethods.MICROSPHERE);
    }

    private Boolean validate() {
        String validationMessage = "";

        try {
            updateModel();
        } catch (Exception ex) {
            validationMessage = validationMessage.concat("Check your parameters");
            JOptionPane.showMessageDialog(view, validationMessage);
            return false;
        }

        if (model.getxMin().equals(0f) || model.getxMax().equals(0f)
                || model.getyMin().equals(0f) || model.getyMax().equals(0f)
                || model.getxMin()*model.getxMax()<0
                || model.getyMin()*model.getyMax()<0) {
            canDraw = false;
            validationMessage = validationMessage.concat("Both x and y can't be zero!");
            validationMessage = validationMessage.concat("\n");
        }
        if (model.getxMin() > model.getxMax()) {
            canDraw = false;
            validationMessage = validationMessage.concat("XMax should be greater then XMin!");
            validationMessage = validationMessage.concat("\n");
        }
        if (model.getyMin() > model.getyMax()) {
            canDraw = false;
            validationMessage = validationMessage.concat("YMax should be greater then YMin!");
            validationMessage = validationMessage.concat("\n");
        }
        if (!canDraw) {
            JOptionPane.showMessageDialog(view, validationMessage);
            return false;
        }

        return true;
    }

    private void updateView() {
        view.getNumberOfPoints().setText(model.getNumberOfPoints().toString());
        view.getBoundsTextFields()[0].setText(model.getxMin().toString());
        view.getBoundsTextFields()[1].setText(model.getxMax().toString());
        view.getBoundsTextFields()[2].setText(model.getyMin().toString());
        view.getBoundsTextFields()[3].setText(model.getyMax().toString());
        view.getIntegrationMethodsComboBox().setSelectedItem(model.getIntegrationMethod());
        view.getPeriodToInterpolate().setText(model.getTimePeriod().toString());
        view.getTimeStep().setText(model.getTimeStep().toString());
        view.getBuildingAngleJComboBox().setSelectedItem(model.getBuildingAngle());
    }

    private ActionListener menuItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(view.getImportDataMenuItem())) {
                try {
                    model = (MainWindowModel) xStream.fromXML(new FileInputStream(OpenFileHelper.open(view)));
                } catch (FileNotFoundException ex) {
                    Application.logger.log(Level.SEVERE, ex.getMessage());
                }
                updateView();
            }
            else if (e.getSource().equals(view.getExportDataMenuItem())) {
                try {
                    File file = OpenFileHelper.open(view);
                    if (file.exists()) {
                        file = new File(file.getAbsolutePath());
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    xStream.registerConverter(mainWindowModelConverter);
                    xStream.toXML(MainWindowController.this.model, fos);
                    JOptionPane.showMessageDialog(view, "Data was exported to " + file.getAbsolutePath());
                } catch (NullPointerException ex) {
                    Application.logger.log(Level.SEVERE, ex.getMessage());
                    JOptionPane.showMessageDialog(view, "No data to export.");
                } catch (FileNotFoundException ex) {
                    Application.logger.log(Level.SEVERE, ex.getMessage());
                }
            }
        }
    };

    private FocusListener focusListener = new FocusAdapter() {
        @Override
        public void focusGained(final FocusEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JTextField tf = (JTextField) e.getComponent();
                    tf.selectAll();
                }
            });
        }
    };
}
