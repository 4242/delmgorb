package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.utils.OpenFileHelper;
import com.organization4242.delmgorb.utils.XmlExporter;
import com.organization4242.delmgorb.utils.XmlImporter;
import com.organization4242.delmgorb.view.DialogWindowView;
import com.organization4242.delmgorb.view.MainWindowView;
import com.organization4242.delmgorb.view.PlotView;
import com.organization4242.delmgorb.view.PlotWindowView;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.Observable;
import java.util.Observer;

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
        view.getImportConfigMenuItem().addActionListener(menuItemActionListener);
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
            XmlExporter.init();
            XmlExporter.exportConfig(view);
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

    private Boolean validate() {
        String validationMessage = "";

        try {
            model.setBounds(new String[]{view.getBoundsTextFields()[0].getText(),
                    view.getBoundsTextFields()[1].getText(),
                    view.getBoundsTextFields()[2].getText(),
                    view.getBoundsTextFields()[3].getText()});

            model.setIntegrationMethod((IntegrationMethods) view.getIntegrationMethodsComboBox().getSelectedItem());
            model.setBuildingAngle((BuildingAngle) view.getBuildingAngleJComboBox().getSelectedItem());
            model.setNumberOfPoints(Integer.parseInt(view.getNumberOfPoints().getText()));
            model.setTimeStep(Double.parseDouble(view.getTimeStep().getText()));
            model.setTimePeriod(Double.parseDouble(view.getPeriodToInterpolate().getText()));
            model.setPhi0(Double.parseDouble(view.getPhiTextField().getText()));
            model.setPsi0(Double.parseDouble(view.getPsiTextField().getText()));
            model.setTheta0(Double.parseDouble(view.getThetaTextField().getText()));
            model.setxMin(Float.parseFloat(model.getBounds()[0]));
            model.setxMax(Float.parseFloat(model.getBounds()[1]));
            model.setyMin(Float.parseFloat(model.getBounds()[2]));
            model.setyMax(Float.parseFloat(model.getBounds()[3]));
            model.setNumberOfSpheres(Integer.parseInt(view.getNumberOfSpheresTextField().getText()));
            model.setInterpolationMethod(InterpolationMethods.MICROSPHERE);
        } catch (Exception ex) {
            validationMessage = validationMessage.concat("Check your parameters");
            JOptionPane.showMessageDialog(view, validationMessage);
            return false;
        }

        if (model.getBounds()[0].equals("0") || model.getBounds()[1].equals("0")
                || model.getBounds()[2].equals("0") || model.getBounds()[3].equals("0")
                || (Double.parseDouble(model.getBounds()[0])*Double.parseDouble(model.getBounds()[1])<0)
                || (Double.parseDouble(model.getBounds()[2])*Double.parseDouble(model.getBounds()[3])<0)) {
            canDraw = false;
            validationMessage = validationMessage.concat("Both x and y can't be zero!");
            validationMessage = validationMessage.concat("\n");
        }
        if ((Double.parseDouble(model.getBounds()[0])>Double.parseDouble(model.getBounds()[1]))) {
            canDraw = false;
            validationMessage = validationMessage.concat("XMax should be greater then XMin!");
            validationMessage = validationMessage.concat("\n");
        }
        if ((Double.parseDouble(model.getBounds()[2])>Double.parseDouble(model.getBounds()[3]))) {
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

    private ActionListener menuItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(view.getImportDataMenuItem())) {
                model.setPointsArray(XmlImporter.importData(view));
            }
            else if (e.getSource().equals(view.getImportConfigMenuItem())) {
                XmlImporter.importConfig(view);
            }
            else if (e.getSource().equals(view.getExportDataMenuItem())) {
                if (XmlExporter.canExport()) {
                    XmlExporter.close(OpenFileHelper.open(view));
                } else {
                    JOptionPane.showMessageDialog(view, "No data to export.");
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
