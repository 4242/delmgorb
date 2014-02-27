package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.*;
import com.organization4242.delmgorb.View.PlotView;
import com.organization4242.delmgorb.View.PlotWindowView;
import com.organization4242.delmgorb.View.MainWindowView;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowController {
    private MainWindowModel model;
    private MainWindowView view;
    private Boolean graphWindowOpened = false;
    Boolean canDraw = true;
    Boolean isPlotGenerating = false;

    String[] bounds;

    IntegrationMethods integrationMethod;
    BuildingAngle buildingAngle;
    int numberOfPoints;
    Double timeStep;
    Double timePeriod;
    float xMin;
    float xMax;
    float yMin;
    float yMax;
    int numberOfSpheres;
    InterpolationMethods interpolationMethod = InterpolationMethods.Microsphere;

    class Task extends SwingWorker<Boolean, Integer> {
        @Override
        protected Boolean doInBackground() throws Exception {
            PlotView plotView = PlotBuilder.build(numberOfPoints, buildingAngle, timePeriod, timeStep, integrationMethod,
                    xMin, xMax, yMin, yMax, interpolationMethod, numberOfSpheres);
            PlotWindowView plotWindowView = new PlotWindowView(plotView);

            plotWindowView.addWindowListener(windowListener);
            plotWindowView.display();
            return true;
        }
    }

    private final Task task = new Task();

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        for (JTextField tf : view.getTextFields()) {
            tf.addFocusListener(focusListener);
        }
        for (JTextField tf : view.getBoundsTextFields()) {
            tf.addFocusListener(focusListener);
        }
        view.getButton().addMouseListener(mouseListener);
        view.getNumberOfPoints().addFocusListener(focusListener);
        view.getTimeStep().addFocusListener(focusListener);
    }

    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            drawPlot();
        }
    };

    private void drawPlot() {
        canDraw = true;

        if (!isPlotGenerating) {
            validate();
            isPlotGenerating = true;
            view.getProgressBar().setIndeterminate(true);
            //view.getButton().setText("Stop");
        } else {
            //task.cancel(true);
            //view.getButton().setText("Draw!");
            return;
        }

        if (!graphWindowOpened && canDraw) {
            System.out.println("Drawing plot:");
            System.out.println("    number of points = " + numberOfPoints);
            System.out.println("    time step = " + timeStep);
            System.out.println("    time period = " + timePeriod);
            System.out.println("    method = " + integrationMethod);
            System.out.println("    xMin = " + xMin);
            System.out.println("    xMax = " + xMax);
            System.out.println("    yMin = " + yMin);
            System.out.println("    yMax = " + yMax);

            try {
                task.execute();
            } catch (NumberIsTooSmallException ex) {
                JOptionPane.showMessageDialog(view, "Number of points is too small");
            }
        }
    }

    private void validate() {
        String validationMessage = "";

        for (JTextField tf : view.getTextFields()) {
            if (tf.getText().equals("")) {
                canDraw = false;
                JOptionPane.showMessageDialog(view, "Please fill all fields");
                return;
            }
        }

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
            xMin = Float.parseFloat(bounds[0]);
            xMax = Float.parseFloat(bounds[1]);
            yMin = Float.parseFloat(bounds[2]);
            yMax = Float.parseFloat(bounds[3]);
            numberOfSpheres = Integer.parseInt(view.getNumberOfSpheresTextField().getText());
            interpolationMethod = InterpolationMethods.Microsphere;
        } catch (Exception ex) {
            canDraw = false;
            validationMessage = validationMessage.concat(ex.toString());
            JOptionPane.showMessageDialog(view, validationMessage);
            return;
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
        }
    }

    //TODO: not working!
    //Prevent opening multiple windows with plots
    private WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
            graphWindowOpened = true;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            graphWindowOpened = false;
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
