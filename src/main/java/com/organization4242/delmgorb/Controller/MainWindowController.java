package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.IntegrationMethods;
import com.organization4242.delmgorb.Model.InterpolationMethods;
import com.organization4242.delmgorb.Model.MainWindowModel;
import com.organization4242.delmgorb.Model.PlotBuilder;
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

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        for (JTextField tf : view.getTextFields()) {
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
        Boolean canDraw = true;
        for (JTextField tf : view.getTextFields()) {
            if (tf.getText().equals("")) {
                canDraw = false;
                JOptionPane.showMessageDialog(view, "Please fill all fields");
            }
        }
        if (!graphWindowOpened && canDraw) {
            //Get all values from view
            IntegrationMethods integrationMethod = (IntegrationMethods) view.getComboBox().getSelectedItem();
            int numberOfPoints = Integer.parseInt(view.getNumberOfPoints().getText());
            Double timeStep = Double.parseDouble(view.getTimeStep().getText());
            float xMin = Float.parseFloat(view.getBoundsTextFields()[0].getText());
            float xMax = Float.parseFloat(view.getBoundsTextFields()[1].getText());
            float yMin = Float.parseFloat(view.getBoundsTextFields()[2].getText());
            float yMax = Float.parseFloat(view.getBoundsTextFields()[3].getText());
            InterpolationMethods interpolationMethod = InterpolationMethods.Microsphere;

            System.out.println("Drawing plot:");
            System.out.println("    number of points = " + numberOfPoints);
            System.out.println("    time step = " + timeStep);
            System.out.println("    method = " + integrationMethod);
            System.out.println("    xMin = " + xMin);
            System.out.println("    xMax = " + xMax);
            System.out.println("    yMin = " + yMin);
            System.out.println("    yMax = " + yMax);
            try {
                //Pass all values fro view to constructor of new window
                PlotView plotView = PlotBuilder.build(numberOfPoints, timeStep, integrationMethod,
                        xMin, xMax, yMin, yMax, interpolationMethod);
                PlotWindowView plotWindowView = new PlotWindowView(plotView);

                plotWindowView.addWindowListener(windowListener);
                plotWindowView.display();
            } catch (NumberIsTooSmallException ex) {
                JOptionPane.showMessageDialog(view, "Number of points is too small");
            }
            //catch (Exception ex) {
            //    System.out.println(ex);
            //}
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
