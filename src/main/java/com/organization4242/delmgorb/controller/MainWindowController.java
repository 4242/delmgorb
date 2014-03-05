package com.organization4242.delmgorb.controller;

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
import java.util.logging.Logger;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowController {
    private MainWindowModel mainWindowModel;
    private DataModel dataModel;
    private MainWindowView view;
    private Boolean canDraw = true;
    private Boolean calculateFromScratch = true;

    private DialogWindowView dialogWindowView;

    private PropertyChangeSupport changes;
    private PlotBuilder builder = new PlotBuilder();

    private XStream xStream = new XStream(new DomDriver());

    private Logger logger = Logger.getLogger("Delmgorb.logger");

    public MainWindowController(MainWindowView view, MainWindowModel mainWindowModel, DataModel dataModel) {
        this.mainWindowModel = mainWindowModel;
        this.dataModel = dataModel;
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
            PlotView plotView = builder.build(view, mainWindowModel, dataModel, calculateFromScratch);
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
        if (dataModel.getPoints() != null) {
            calculateFromScratch = JOptionPane.showOptionDialog(view, "Calculate new data?", "", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, null) == 0;
        }

        canDraw = validate();

        if (canDraw) {
            try {
                final Task task = new Task();
                task.execute();
                if (calculateFromScratch) {
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
                            dataModel.setPoints(null);
                            dialogWindowView.dispose();
                        }
                    });
                }
            } catch (NumberIsTooSmallException ex) {
                JOptionPane.showMessageDialog(view, "Number of points is too small");
            }
        }
    }

    private void updateModel() {
        mainWindowModel.setIntegrationMethod((IntegrationMethods) view.getIntegrationMethodsComboBox().getSelectedItem());
        mainWindowModel.setAngle((Angle) view.getAngleJComboBox().getSelectedItem());
        mainWindowModel.setNumberOfPoints(Integer.parseInt(view.getNumberOfPoints().getText()));
        mainWindowModel.setTimeStep(Double.parseDouble(view.getTimeStep().getText()));
        mainWindowModel.setTimePeriod(Double.parseDouble(view.getPeriodToInterpolate().getText()));
        mainWindowModel.setPhi0(Double.parseDouble(view.getPhiTextField().getText()));
        mainWindowModel.setPsi0(Double.parseDouble(view.getPsiTextField().getText()));
        mainWindowModel.setTheta0(Double.parseDouble(view.getThetaTextField().getText()));
        mainWindowModel.setxMin(Float.parseFloat(view.getBoundsTextFields()[0].getText()));
        mainWindowModel.setxMax(Float.parseFloat(view.getBoundsTextFields()[1].getText()));
        mainWindowModel.setyMin(Float.parseFloat(view.getBoundsTextFields()[2].getText()));
        mainWindowModel.setyMax(Float.parseFloat(view.getBoundsTextFields()[3].getText()));
        mainWindowModel.setNumberOfSpheres(Integer.parseInt(view.getNumberOfSpheresTextField().getText()));
        mainWindowModel.setInterpolationMethod(InterpolationMethods.MICROSPHERE);
    }

    private Boolean validate() {
        canDraw = true;
        String validationMessage = "";

        try {
            updateModel();
        } catch (Exception ex) {
            validationMessage = validationMessage.concat("Check your parameters");
            JOptionPane.showMessageDialog(view, validationMessage);
            return false;
        }

        if (mainWindowModel.getxMin().equals(0f) || mainWindowModel.getxMax().equals(0f)
                || mainWindowModel.getyMin().equals(0f) || mainWindowModel.getyMax().equals(0f)
                || mainWindowModel.getxMin()* mainWindowModel.getxMax()<0
                || mainWindowModel.getyMin()* mainWindowModel.getyMax()<0) {
            canDraw = false;
            validationMessage = validationMessage.concat("Both x and y can't be zero!");
            validationMessage = validationMessage.concat("\n");
        }
        if (mainWindowModel.getxMin() > mainWindowModel.getxMax()) {
            canDraw = false;
            validationMessage = validationMessage.concat("XMax should be greater then XMin!");
            validationMessage = validationMessage.concat("\n");
        }
        if (mainWindowModel.getyMin() > mainWindowModel.getyMax()) {
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
        view.getNumberOfPoints().setText(mainWindowModel.getNumberOfPoints().toString());
        view.getBoundsTextFields()[0].setText(mainWindowModel.getxMin().toString());
        view.getBoundsTextFields()[1].setText(mainWindowModel.getxMax().toString());
        view.getBoundsTextFields()[2].setText(mainWindowModel.getyMin().toString());
        view.getBoundsTextFields()[3].setText(mainWindowModel.getyMax().toString());
        view.getIntegrationMethodsComboBox().setSelectedItem(mainWindowModel.getIntegrationMethod());
        view.getPeriodToInterpolate().setText(mainWindowModel.getTimePeriod().toString());
        view.getTimeStep().setText(mainWindowModel.getTimeStep().toString());
        view.getAngleJComboBox().setSelectedItem(mainWindowModel.getAngle());
        view.getPhiTextField().setText(mainWindowModel.getPhi().toString());
        view.getPsiTextField().setText(mainWindowModel.getPsi().toString());
        view.getThetaTextField().setText(mainWindowModel.getTheta().toString());
        view.getNumberOfSpheresTextField().setText(mainWindowModel.getNumberOfSpheres().toString());
    }

    private ActionListener menuItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(view.getImportDataMenuItem())) {
                try {
                    Serializator serializator =
                            (Serializator) xStream.fromXML(new FileInputStream(OpenFileHelper.open(view)));
                    mainWindowModel = serializator.mainWindowModel;
                    dataModel = serializator.dataModel;
                } catch (FileNotFoundException ex) {
                    logger.log(Level.SEVERE, ex.getMessage());
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
                    Serializator serializator = new Serializator(mainWindowModel, dataModel);
                    xStream.omitField(Observable.class, "obs");
                    xStream.omitField(Observable.class, "changed");
                    xStream.toXML(serializator, fos);
                    JOptionPane.showMessageDialog(view, "Data was exported to " + file.getAbsolutePath());
                } catch (NullPointerException ex) {
                    logger.log(Level.SEVERE, ex.getMessage());
                    JOptionPane.showMessageDialog(view, "No data to export.");
                } catch (FileNotFoundException ex) {
                    logger.log(Level.SEVERE, ex.getMessage());
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
