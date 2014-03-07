package com.organization4242.delmgorb.model;

import com.organization4242.delmgorb.controller.MainWindowController;

import java.io.Serializable;

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
    private Double phi0;
    private Double psi0;
    private Double theta0;
    private Integer numberOfSpheres;
    private InterpolationMethods interpolationMethod = InterpolationMethods.MICROSPHERE;

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
        return phi0;
    }

    public Double getPsi() {
        return psi0;
    }

    public Double getTheta() {
        return theta0;
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

    public InterpolationMethods getInterpolationMethod() {
        return interpolationMethod;
    }

    public void setNumberOfPoints(Integer numberOfPoints) {
        int oldValue = this.numberOfPoints != null ? this.numberOfPoints : 0;
        this.numberOfPoints = numberOfPoints;
        firePropertyChange(MainWindowController.NUMBER_OF_POINTS, oldValue, numberOfPoints);
    }

    public void setTimeStep(Double timeStep) {
        Double oldValue = this.timeStep;
        this.timeStep = timeStep;
        firePropertyChange(MainWindowController.TIME_STEP, oldValue, timeStep);
    }

    public void setTimePeriod(Double timePeriod) {
        Double oldValue = this.timeStep;
        this.timePeriod = timePeriod;
        firePropertyChange(MainWindowController.TIME_PERIOD, oldValue, timePeriod);
    }

    public void setIntegrationMethod(IntegrationMethods integrationMethod) {
        IntegrationMethods oldValue = this.integrationMethod;
        this.integrationMethod = integrationMethod;
        firePropertyChange(MainWindowController.INTEGRATION_METHOD, oldValue, integrationMethod);
    }

    public void setAngle(Angle angle) {
        Angle oldValue = this.angle;
        this.angle = angle;
        firePropertyChange(MainWindowController.ANGLE, oldValue, angle);
    }

    public void setXMin(Float xMin) {
        Float oldValue = this.xMin;
        this.xMin = xMin;
        firePropertyChange(MainWindowController.X_MIN, oldValue, xMin);
    }

    public void setXMax(Float xMax) {
        Float oldValue = this.xMax;
        this.xMax = xMax;
        firePropertyChange(MainWindowController.X_MAX, oldValue, xMax);
    }

    public void setYMin(Float yMin) {
        Float oldValue = this.yMin;
        this.yMin = yMin;
        firePropertyChange(MainWindowController.Y_MIN, oldValue, yMin);
    }

    public void setYMax(Float yMax) {
        Float oldValue = this.yMax;
        this.yMax = yMax;
        firePropertyChange(MainWindowController.Y_MAX, oldValue, yMax);
    }

    public void setPhi0(Double phi0) {
        Double oldValue = this.phi0;
        this.phi0 = phi0;
        firePropertyChange(MainWindowController.PHI0, oldValue, phi0);
    }

    public void setPsi0(Double psi0) {
        Double oldValue = this.psi0;
        this.psi0 = psi0;
        firePropertyChange(MainWindowController.PSI0, oldValue, psi0);
    }

    public void setTheta0(Double theta0) {
        Double oldValue = this.theta0;
        this.theta0 = theta0;
        firePropertyChange(MainWindowController.THETA0, oldValue, theta0);
    }

    public void setNumberOfSpheres(Integer numberOfSpheres) {
        Integer oldValue = this.numberOfSpheres;
        this.numberOfSpheres = numberOfSpheres;
        firePropertyChange(MainWindowController.NUMBER_OF_SPHERES, oldValue, numberOfSpheres);
    }

    public MainWindowModel() {

    }
}
