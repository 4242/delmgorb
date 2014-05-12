package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.*;
import com.organization4242.delmgorb.view.MainWindowView;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Observable;

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
    public static final String X_MIN = "XMin";
    public static final String X_MAX = "XMax";
    public static final String Y_MIN = "YMin";
    public static final String Y_MAX = "YMax";
    public static final String PHI = "Phi";
    public static final String PSI = "Psi";
    public static final String THETA = "Theta";
    public static final String NUMBER_OF_SPHERES = "NumberOfSpheres";
    public static final String IMPORT = "Import";
    public static final String EXPORT = "Export";
    public static final String DRAW_BUTTON_CLICK = "DrawButtonClicked";
    public static final String RESET_BUTTON_CLICK = "ResetButtonClicked";

    private MainWindowModel mainWindowModel;
    private DataModel dataModel;
    private MainWindowView mainWindowView;
    private PlotBuilder plotBuilder;
    private Boolean calculateFromScratch = true;
    private Boolean imported = false;

    private XStream xStream;

    private Logger logger = LogManager.getLogger(MainWindowController.class);

    @Required
    public void setMainWindowModel(MainWindowModel mainWindowModel) {
        this.mainWindowModel = mainWindowModel;
        this.dataModel = new DataModel();
        this.dataModel.setMainWindowModel(this.mainWindowModel);
        addModel(this.mainWindowModel);
        mainWindowModel.setDefaults();
    }

    @Required
    public void setMainWindowView(MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
        addView(this.mainWindowView);
    }

    @Required
    public void setPlotBuilder(PlotBuilder plotBuilder) {
        this.plotBuilder = plotBuilder;
        this.plotBuilder.setDataModel(dataModel);
        this.plotBuilder.setMainWindowModel(mainWindowModel);
    }

    public void setxStream(XStream xStream) {
        this.xStream = xStream;
    }

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
        mainWindowView.getPsiTextField().setEnabled(enabled);
        mainWindowView.getPhiTextField().setEnabled(enabled);
        mainWindowView.getThetaTextField().setEnabled(enabled);
    }

    /**
     * Custom event handling.
     *
     * @param pce event from {@link com.organization4242.delmgorb.view.MainWindowView}
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case DRAW_BUTTON_CLICK: {
                if (MainWindowController.this.dataModel.getPoints() != null && !imported) {
                    calculateFromScratch = JOptionPane.showOptionDialog(MainWindowController.this.mainWindowView.getFrame(),
                            "Calculate new data?", "", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, null) == 0;
                }

                if (imported) {
                    calculateFromScratch = false;
                }

                plotBuilder.setDataModel(dataModel);
                plotBuilder.setMainWindowModel(mainWindowModel);
                plotBuilder.setCalculateFromScratch(calculateFromScratch);
                calculateFromScratch = true;
                SwingWorker task = plotBuilder.getTask();
                task.execute();
                break;
            } case RESET_BUTTON_CLICK: {
                if (JOptionPane.showOptionDialog(MainWindowController.this.mainWindowView.getFrame(),
                        "Reset data?", "", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, null) == 0) {
                    mainWindowModel.setDefaults();
                    dataModel.setPoints(null);
                    setControlsEnabled(true);
                    imported = false;
                }
                break;
            } case IMPORT: {
                try {
                    File file = OpenFileHelper.open(mainWindowView.getFrame());
                    if (file != null) {
                        Serializer serializer =
                                (Serializer) xStream.fromXML(new FileInputStream(file));
                        mainWindowModel.update(serializer.getMainWindowModel());
                        dataModel = serializer.getDataModel();
                        setControlsEnabled(false);
                        imported = true;
                    }
                } catch (FileNotFoundException ex) {
                    logger.error(ex);
                }
                break;
            } case EXPORT: {
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
                    logger.error(ex);
                } catch (Exception ex) {
                    logger.error(ex);
                    JOptionPane.showMessageDialog(mainWindowView.getFrame(), "Something is wrong.");
                }
                break;
            } default: {
                super.propertyChange(pce);
                break;
            }
        }
    }
}
