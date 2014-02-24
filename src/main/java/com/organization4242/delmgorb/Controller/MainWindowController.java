package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.*;
import com.organization4242.delmgorb.View.GraphView;
import com.organization4242.delmgorb.View.GraphWindowView;
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
    private int numberOfPoints;
    private double timeStep;
    private Boolean graphWindowOpened = false;

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        numberOfPoints = Integer.parseInt(view.getNumberOfPoints().getText());
        timeStep = Double.parseDouble(view.getTimeStep().getText());
        for (JTextField tf : view.getTextFields()) {
            tf.addFocusListener(focusListener);
        }
        view.getButton().addMouseListener(mouseListener);
        view.getComboBox().addItemListener(itemListener);
        view.getNumberOfPoints().addFocusListener(focusListener);
        view.getTimeStep().addFocusListener(focusListener);
    }

    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Boolean canDraw = true;
            for (JTextField tf : view.getTextFields()) {
                if (tf.getText().equals("")) {
                    canDraw = false;
                    JOptionPane.showMessageDialog(view, "Please fill all fields");
                }
            }
            if (!graphWindowOpened && canDraw) {
                IntegrationMethods method = (IntegrationMethods) view.getComboBox().getSelectedItem();
                System.out.println("Drawing plot:");
                System.out.println("    number of points = " + numberOfPoints);
                System.out.println("    time step = " + timeStep);
                System.out.println("    method = " + method);
                GraphWindowView graphWindowView;
                try {
                    graphWindowView = new GraphWindowView(new GraphView(
                        new GraphModel(new InterpolatorModel()
                            .getFunction(new DataModel(numberOfPoints, timeStep,
                                    method).getListOfPoints()))));
                    graphWindowView.addWindowListener(windowListener);
                    graphWindowView.display();
                } catch (NumberIsTooSmallException ex) {
                    JOptionPane.showMessageDialog(view, "Number of points is too small");
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    };

    private InputMethodListener inputMethodListener = new InputMethodListener() {
        @Override
        public void inputMethodTextChanged(InputMethodEvent e) {
            System.out.println(e.getSource());
            if (e.getSource().equals(view.getNumberOfPoints())) {
                numberOfPoints = Integer.parseInt(view.getNumberOfPoints().getText());
            }
            else if (e.getSource().equals(view.getTimeStep())) {
                timeStep = Double.parseDouble(view.getTimeStep().getText());
            }
        }

        @Override
        public void caretPositionChanged(InputMethodEvent event) {

        }
    };

    private ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED)
                System.out.println(((JComboBox) e.getSource()).getSelectedItem());
        }
    };

    //TODO: not working!
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

        @Override
        public void focusLost(final FocusEvent e) {
            System.out.println(((JTextField) e.getSource()).getText());
            if (e.getSource().equals(view.getNumberOfPoints())) {
                numberOfPoints = Integer.parseInt(view.getNumberOfPoints().getText());
            }
            else if (e.getSource().equals(view.getTimeStep())) {
                timeStep = Double.parseDouble(view.getTimeStep().getText());
            }
        }
    };
}
