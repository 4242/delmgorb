package com.organization4242.delmgorb.model;

import java.util.Arrays;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowModel {
    private PointsArray pointsArray;

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
    private InterpolationMethods interpolationMethod = InterpolationMethods.MICROSPHERE;

    public PointsArray getPointsArray() {
        return pointsArray;
    }

    public void setPointsArray(PointsArray pointsArray) {
        this.pointsArray = pointsArray;
    }

    public String[] getBounds() {
        return bounds;
    }

    public IntegrationMethods getIntegrationMethod() {
        return integrationMethod;
    }

    public BuildingAngle getBuildingAngle() {
        return buildingAngle;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public Double getTimeStep() {
        return timeStep;
    }

    public Double getTimePeriod() {
        return timePeriod;
    }

    public Double getPhi0() {
        return phi0;
    }

    public Double getPsi0() {
        return psi0;
    }

    public Double getTheta0() {
        return theta0;
    }

    public float getxMin() {
        return xMin;
    }

    public float getxMax() {
        return xMax;
    }

    public float getyMin() {
        return yMin;
    }

    public float getyMax() {
        return yMax;
    }

    public int getNumberOfSpheres() {
        return numberOfSpheres;
    }

    public InterpolationMethods getInterpolationMethod() {
        return interpolationMethod;
    }

    public void setBounds(String[] newBounds) {
        this.bounds = Arrays.copyOf(newBounds, newBounds.length);
    }

    public void setIntegrationMethod(IntegrationMethods integrationMethod) {
        this.integrationMethod = integrationMethod;
    }

    public void setBuildingAngle(BuildingAngle buildingAngle) {
        this.buildingAngle = buildingAngle;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public void setTimeStep(Double timeStep) {
        this.timeStep = timeStep;
    }

    public void setTimePeriod(Double timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setPhi0(Double phi0) {
        this.phi0 = phi0;
    }

    public void setPsi0(Double psi0) {
        this.psi0 = psi0;
    }

    public void setTheta0(Double theta0) {
        this.theta0 = theta0;
    }

    public void setxMin(float xMin) {
        this.xMin = xMin;
    }

    public void setxMax(float xMax) {
        this.xMax = xMax;
    }

    public void setyMin(float yMin) {
        this.yMin = yMin;
    }

    public void setyMax(float yMax) {
        this.yMax = yMax;
    }

    public void setNumberOfSpheres(int numberOfSpheres) {
        this.numberOfSpheres = numberOfSpheres;
    }

    public void setInterpolationMethod(InterpolationMethods interpolationMethod) {
        this.interpolationMethod = interpolationMethod;
    }
}
