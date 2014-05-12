package com.organization4242.delmgorb.model;

import com.organization4242.delmgorb.controller.MainWindowController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * Class is used for storing values from {@link com.organization4242.delmgorb.view.MainWindowView}
 * and pass it to {@link com.organization4242.delmgorb.model.DataModel}.
 *
 * @author Murzinov Ilya
 */
public class MainWindowModel extends AbstractModel implements Serializable {
    private Integer numberOfPoints;
    private Double timeStep;
    private Double timePeriod;
    private IntegrationMethods integrationMethod;
    private Angle angle;
    private Float xMin;
    private Float xMax;
    private Float yMin;
    private Float yMax;
    private Double phi;
    private Double psi;
    private Double theta;
    private Integer numberOfSpheres;

    private transient Logger logger = LogManager.getLogger(MainWindowModel.class);

    public IntegrationMethods getIntegrationMethod() {
        return integrationMethod;
    }

    public Angle getAngle() {
        return angle;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }

    public Double getTimeStep() {
        return timeStep;
    }

    public Double getTimePeriod() {
        return timePeriod;
    }

    public Double getPhi() {
        return phi;
    }

    public Double getPsi() {
        return psi;
    }

    public Double getTheta() {
        return theta;
    }

    public Float getXMin() {
        return xMin;
    }

    public Float getXMax() {
        return xMax;
    }

    public Float getYMin() {
        return yMin;
    }

    public Float getYMax() {
        return yMax;
    }

    public Integer getNumberOfSpheres() {
        return numberOfSpheres;
    }

    public void setNumberOfPoints(String numberOfPoints) {
        this.numberOfPoints = numberOfPoints.equals("") ? 0 : Integer.parseInt(numberOfPoints);
    }

    public void setTimeStep(String timeStep) {
        this.timeStep = timeStep.equals("") ? 0 : Double.parseDouble(timeStep);
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod.equals("") ? 0 : Double.parseDouble(timePeriod);
    }

    public void setIntegrationMethod(String integrationMethod) {
        this.integrationMethod = IntegrationMethods.fromString(integrationMethod);
    }

    public void setAngle(String angle) {
        this.angle = Angle.fromString(angle);
    }

    public void setXMin(String xMin) {
        this.xMin = xMin.equals("") ? 0 : Float.parseFloat(xMin);
    }

    public void setXMax(String xMax) {
        this.xMax = xMax.equals("") ? 0 : Float.parseFloat(xMax);
    }

    public void setYMin(String yMin) {
        this.yMin = yMin.equals("") ? 0 : Float.parseFloat(yMin);
    }

    public void setYMax(String yMax) {
        this.yMax = yMax.equals("") ? 0 : Float.parseFloat(yMax);
    }

    public void setPhi(String phi) {
        this.phi = phi.equals("") ? 0 : Double.parseDouble(phi);
    }

    public void setPsi(String psi) {
        this.psi = psi.equals("") ? 0 : Double.parseDouble(psi);
    }

    public void setTheta(String theta) {
        this.theta = theta.equals("") ? 0 : Double.parseDouble(theta);
    }

    public void setNumberOfSpheres(String numberOfSpheres) {
        this.numberOfSpheres = numberOfSpheres.equals("") ? 0 : Integer.parseInt(numberOfSpheres);
    }

    public void setNumberOfPoints(Integer numberOfPoints) {
        int oldValue = this.numberOfPoints != null ? this.numberOfPoints : 0;
        this.numberOfPoints = numberOfPoints;
        firePropertyChange(MainWindowController.NUMBER_OF_POINTS, oldValue, numberOfPoints);
    }

    public void setTimeStep(Double timeStep) {
        Double oldValue = this.timeStep != null ? this.timeStep : 0d;
        this.timeStep = timeStep;
        firePropertyChange(MainWindowController.TIME_STEP, oldValue, timeStep);
    }

    public void setTimePeriod(Double timePeriod) {
        Double oldValue = this.timePeriod != null ? this.timePeriod : 0d;
        this.timePeriod = timePeriod;
        firePropertyChange(MainWindowController.TIME_PERIOD, oldValue, timePeriod);
    }

    public void setIntegrationMethod(IntegrationMethods integrationMethod) {
        IntegrationMethods oldValue = this.integrationMethod != null ? this.integrationMethod : null;
        this.integrationMethod = integrationMethod;
        firePropertyChange(MainWindowController.INTEGRATION_METHOD, oldValue, integrationMethod);
    }

    public void setAngle(Angle angle) {
        Angle oldValue = this.angle != null ? this.angle : null;
        this.angle = angle;
        firePropertyChange(MainWindowController.ANGLE, oldValue, angle);
    }

    public void setXMin(Float xMin) {
        Float oldValue = this.xMin != null ? this.xMin : 0f;
        this.xMin = xMin;
        firePropertyChange(MainWindowController.X_MIN, oldValue, xMin);
    }

    public void setXMax(Float xMax) {
        Float oldValue = this.xMax != null ? this.xMax : 0f;
        this.xMax = xMax;
        firePropertyChange(MainWindowController.X_MAX, oldValue, xMax);
    }

    public void setYMin(Float yMin) {
        Float oldValue = this.yMin != null ? this.yMin : 0f;
        this.yMin = yMin;
        firePropertyChange(MainWindowController.Y_MIN, oldValue, yMin);
    }

    public void setYMax(Float yMax) {
        Float oldValue = this.yMax != null ? this.yMax : 0f;
        this.yMax = yMax;
        firePropertyChange(MainWindowController.Y_MAX, oldValue, yMax);
    }

    public void setPhi(Double phi) {
        Double oldValue = this.phi != null ? this.phi : 0d;
        this.phi = phi;
        firePropertyChange(MainWindowController.PHI, oldValue, phi);
    }

    public void setPsi(Double psi) {
        Double oldValue = this.psi != null ? this.psi : 0d;
        this.psi = psi;
        firePropertyChange(MainWindowController.PSI, oldValue, psi);
    }

    public void setTheta(Double theta) {
        Double oldValue = this.theta != null ? this.theta : 0d;
        this.theta = theta;
        firePropertyChange(MainWindowController.THETA, oldValue, theta);
    }

    public void setNumberOfSpheres(Integer numberOfSpheres) {
        Integer oldValue = this.numberOfSpheres != null ? this.numberOfSpheres : null;
        this.numberOfSpheres = numberOfSpheres;
        firePropertyChange(MainWindowController.NUMBER_OF_SPHERES, oldValue, numberOfSpheres);
    }

    public MainWindowModel() {

    }

    public void setDefaults() {
        setNumberOfPoints(10);
        setTimeStep(0.5);
        setTimePeriod(100d);
        setXMin(1f);
        setXMax(2f);
        setYMin(0.05f);
        setYMax(1f);
        setAngle(Angle.PSI);
        setIntegrationMethod(IntegrationMethods.DORMAND_PRINCE_8);
        setPhi(0.01);
        setPsi(0.01);
        setTheta(0.01);
        setNumberOfSpheres(50);
    }

    public void update(MainWindowModel mainWindowModel) {
        setNumberOfPoints(mainWindowModel.getNumberOfPoints());
        setTimeStep(mainWindowModel.getTimeStep());
        setTimePeriod(mainWindowModel.getTimePeriod());
        setXMin(mainWindowModel.getXMin());
        setXMax(mainWindowModel.getXMax());
        setYMin(mainWindowModel.getYMin());
        setYMax(mainWindowModel.getYMax());
        setIntegrationMethod(mainWindowModel.getIntegrationMethod());
        setAngle(mainWindowModel.getAngle());
        setPhi(mainWindowModel.getPhi());
        setPsi(mainWindowModel.getPsi());
        setTheta(mainWindowModel.getTheta());
        setNumberOfSpheres(mainWindowModel.getNumberOfSpheres());
    }

    @Override
    public void viewPropertyChange(PropertyChangeEvent pce) {
        try {
            MainWindowModel.class.getMethod("set"+pce.getPropertyName(), String.class).invoke(this, pce.getNewValue());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException ex) {
            logger.error(ex);
        }
    }
}
