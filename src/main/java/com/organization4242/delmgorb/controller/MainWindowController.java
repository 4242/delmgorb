package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.view.MainWindowView;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Required;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents controller used to bind {@link com.organization4242.delmgorb.model.MainWindowModel}
 * and {@link com.organization4242.delmgorb.view.MainWindowView}.
 *
 * @author Murzinov Ilya
 */
public class MainWindowController extends AbstractController {
    public static final String NUMBER_OF_POINTS = "NumberOfPoints";
    public static final String TIME_STEP = "TimeStep";
    public static final String TIME_PERIOD = "TimePeriod";
    public static final String INTEGRATION_METHOD = "IntegrationMethod";
    public static final String ANGLE = "Angle";
    public static final String X_MIN = "xMin";
    public static final String X_MAX = "xMax";
    public static final String Y_MIN = "yMin";
    public static final String Y_MAX = "yMax";
    public static final String PHI = "Phi";
    public static final String PSI = "Psi";
    public static final String THETA = "Theta";
    public static final String NUMBER_OF_SPHERES = "NumberOfSpheres";

    private MainWindowModel mainWindowModel;
    private DataModel dataModel;
    private MainWindowView mainWindowView;
    private PlotBuilder plotBuilder;
    private Boolean calculateFromScratch = true;

    private XStream xStream;

    private Logger logger = Logger.getLogger("Delmgorb.logger");

    private SwingWorker task;

    @Required
    public void setMainWindowModel(MainWindowModel mainWindowModel) {
        this.mainWindowModel = mainWindowModel;
        addModel(this.mainWindowModel);
    }

    @Required
    public void setMainWindowView(MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
        addView(this.mainWindowView);
        addActionListeners();
    }

    @Required
    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Required
    public void setPlotBuilder(PlotBuilder plotBuilder) {
        this.plotBuilder = plotBuilder;
    }

    public void setxStream(XStream xStream) {
        this.xStream = xStream;
    }

    public MainWindowController() {

    }

    /**
    * Adds all listeners to controls.
    */
    public void addActionListeners() {
        mainWindowView.getImportDataMenuItem().addActionListener(menuItemActionListener);
        mainWindowView.getExportDataMenuItem().addActionListener(menuItemActionListener);

        //Perform calculation and drawing.
        mainWindowView.getButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MainWindowController.this.dataModel.getPoints() != null) {
                    calculateFromScratch = JOptionPane.showOptionDialog(MainWindowController.this.mainWindowView.getFrame(),
                            "Calculate new data?", "", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, null) == 0;
                }
                plotBuilder.getDialogWindowView().setLocationRelativeTo(MainWindowController.this.mainWindowView.getFrame());
                plotBuilder.setCalculateFromScratch(calculateFromScratch);
                task = plotBuilder.getTask();
                task.execute();
            }
        });
    }

    /**
    * Action listener for menu items in main window.
    * 
    * Performs serialization/deserialization {@link com.organization4242.delmgorb.model.MainWindowModel}
    * and {@link com.organization4242.delmgorb.model.DataModel}.
    */
    private ActionListener menuItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(mainWindowView.getImportDataMenuItem())) {
                try {
                    Serializer serializer =
                            (Serializer) xStream.fromXML(new FileInputStream(OpenFileHelper.open(mainWindowView.getFrame())));
                    mainWindowModel = serializer.getMainWindowModel();
                    dataModel = serializer.getDataModel();
                    setControlsEnabled(false);
                } catch (FileNotFoundException ex) {
                    logger.log(Level.SEVERE, ex.getMessage());
                }
            }
            else if (e.getSource().equals(mainWindowView.getExportDataMenuItem())) {
                try {
                    File file = OpenFileHelper.open(mainWindowView.getFrame());
                    if (file.exists()) {
                        file = new File(file.getAbsolutePath());
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    Serializer serializer = new Serializer(mainWindowModel, dataModel);
                    xStream.omitField(Observable.class, "obs");
                    xStream.omitField(Observable.class, "changed");
                    xStream.omitField(AbstractModel.class, "propertyChangeSupport");
                    xStream.omitField(DataModel.class, "mainWindowModel");
                    xStream.toXML(serializer, fos);
                    JOptionPane.showMessageDialog(mainWindowView.getFrame(), "Data was exported to " + file.getAbsolutePath());
                }  catch (FileNotFoundException ex) {
                    logger.severe(ex.getMessage());
                } catch (Exception ex) {
                    logger.severe(ex.getMessage());
                    JOptionPane.showMessageDialog(mainWindowView.getFrame(), "Something is wrong.");
                }
            }
        }
    };

    private void setControlsEnabled(Boolean enabled) {
        mainWindowView.getNumberOfPointsTextField().setEnabled(enabled);
        mainWindowView.getIntegrationMethodsComboBox().setEnabled(enabled);
        mainWindowView.getAngleComboBox().setEnabled(enabled);
        mainWindowView.getxMaxTextField().setEnabled(enabled);
        mainWindowView.getxMinTextField().setEnabled(enabled);
        mainWindowView.getyMinTextField().setEnabled(enabled);
        mainWindowView.getyMaxTextField().setEnabled(enabled);
        mainWindowView.getTimeStepTextField().setEnabled(enabled);
        mainWindowView.getTimePeriodTextField().setEnabled(enabled);
    }
}
