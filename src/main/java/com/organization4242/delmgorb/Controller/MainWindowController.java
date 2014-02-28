package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.*;
import com.organization4242.delmgorb.View.DialogWindowView;
import com.organization4242.delmgorb.View.MainWindowView;
import com.organization4242.delmgorb.View.PlotView;
import com.organization4242.delmgorb.View.PlotWindowView;
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

    private String[] bounds;

    private IntegrationMethods integrationMethod;
    private BuildingAngle buildingAngle;
    private int numberOfPoints;
    private Double timeStep;
    private Double timePeriod;
    private Double phi0;
    private Double psi0;
    private Double theta0;
    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    private int numberOfSpheres;
    private InterpolationMethods interpolationMethod = InterpolationMethods.Microsphere;

    private DialogWindowView dialogWindowView;

    private PropertyChangeSupport changes;
    private PlotBuilder builder;

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        for (JTextField tf : view.getTextFields()) {
            tf.addFocusListener(focusListener);
        }
        for (JTextField tf : view.getBoundsTextFields()) {
            tf.addFocusListener(focusListener);
        }
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
            builder = new PlotBuilder();
            builder.addObserver(this);
            PlotView plotView = builder.build(numberOfPoints, buildingAngle, timePeriod, timeStep, phi0, theta0, psi0,
                    integrationMethod, xMin, xMax, yMin, yMax, interpolationMethod, numberOfSpheres);
            PlotWindowView plotWindowView = new PlotWindowView(plotView);
            plotWindowView.display();
            return null;
        }

        public void done() {
            view.setEnabled(true);
        }

        @Override
        public void update(Observable o, Object arg) {
            if (arg.getClass().equals(Integer.class))
                dialogWindowView.getProgressBar().setValue((Integer) arg);
            if (arg.getClass().equals(String.class)) {
                dialogWindowView.dispose();
            }
        }
    }

    private void drawPlot() {
        canDraw = validate();

        if (canDraw) {
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
        }
    }

    private Boolean validate() {
        String validationMessage = "";

        try {
            bounds = new String[]{view.getBoundsTextFields()[0].getText(),
                    view.getBoundsTextFields()[1].getText(),
                    view.getBoundsTextFields()[2].getText(),
                    view.getBoundsTextFields()[3].getText()};

            integrationMethod = (IntegrationMethods) view.getIntegrationMethodsComboBox().getSelectedItem();
            buildingAngle = (BuildingAngle) view.getBuildingAngleJComboBox().getSelectedItem();
            numberOfPoints = Integer.parseInt(view.getNumberOfPoints().getText());
            timeStep = Double.parseDouble(view.getTimeStep().getText());
            timePeriod = Double.parseDouble(view.getPeriodToInterpolate().getText());
            phi0 = Double.parseDouble(view.getPhiTextField().getText());
            psi0 = Double.parseDouble(view.getPsiTextField().getText());
            theta0 = Double.parseDouble(view.getThetaTextField().getText());
            xMin = Float.parseFloat(bounds[0]);
            xMax = Float.parseFloat(bounds[1]);
            yMin = Float.parseFloat(bounds[2]);
            yMax = Float.parseFloat(bounds[3]);
            numberOfSpheres = Integer.parseInt(view.getNumberOfSpheresTextField().getText());
            interpolationMethod = InterpolationMethods.Microsphere;
        } catch (Exception ex) {
            validationMessage = validationMessage.concat("Check your parameters");
            JOptionPane.showMessageDialog(view, validationMessage);
            return false;
        }

        if (bounds[0].equals("0") || bounds[1].equals("0")
                || bounds[2].equals("0") || bounds[3].equals("0")
                || (Double.parseDouble(bounds[0])*Double.parseDouble(bounds[1])<0)
                || (Double.parseDouble(bounds[2])*Double.parseDouble(bounds[3])<0)) {
            canDraw = false;
            validationMessage = validationMessage.concat("Both x and y can't be zero!");
            validationMessage = validationMessage.concat("\n");
        }
        if ((Double.parseDouble(bounds[0])>Double.parseDouble(bounds[1]))) {
            canDraw = false;
            validationMessage = validationMessage.concat("XMax should be greater then XMin!");
            validationMessage = validationMessage.concat("\n");
        }
        if ((Double.parseDouble(bounds[2])>Double.parseDouble(bounds[3]))) {
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
