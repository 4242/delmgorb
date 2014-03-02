package com.organization4242.delmgorb.model;

import java.io.Serializable;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowModel implements Serializable {
    private PointsArray pointsArray;

    private IntegrationMethods integrationMethod;
    private BuildingAngle buildingAngle;
    private Integer numberOfPoints;
    private Double timeStep;
    private Double timePeriod;
    private Double phi0;
    private Double psi0;
    private Double theta0;
    private Float xMin;
    private Float xMax;
    private Float yMin;
    private Float yMax;
    private Integer numberOfSpheres;
    private InterpolationMethods interpolationMethod = InterpolationMethods.MICROSPHERE;

    public PointsArray getPointsArray() {
        return pointsArray;
    }

    public void setPointsArray(PointsArray pointsArray) {
        this.pointsArray = pointsArray;
    }

    public IntegrationMethods getIntegrationMethod() {
        return integrationMethod;
    }

    public BuildingAngle getBuildingAngle() {
        return buildingAngle;
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

    public Double getPhi0() {
        return phi0;
    }

    public Double getPsi0() {
        return psi0;
    }

    public Double getTheta0() {
        return theta0;
    }

    public Float getxMin() {
        return xMin;
    }

    public Float getxMax() {
        return xMax;
    }

    public Float getyMin() {
        return yMin;
    }

    public Float getyMax() {
        return yMax;
    }

    public Integer getNumberOfSpheres() {
        return numberOfSpheres;
    }

    public InterpolationMethods getInterpolationMethod() {
        return interpolationMethod;
    }

    public void setIntegrationMethod(IntegrationMethods integrationMethod) {
        this.integrationMethod = integrationMethod;
    }

    public void setBuildingAngle(BuildingAngle buildingAngle) {
        this.buildingAngle = buildingAngle;
    }

    public void setNumberOfPoints(Integer numberOfPoints) {
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

    public void setxMin(Float xMin) {
        this.xMin = xMin;
    }

    public void setxMax(Float xMax) {
        this.xMax = xMax;
    }

    public void setyMin(Float yMin) {
        this.yMin = yMin;
    }

    public void setyMax(Float yMax) {
        this.yMax = yMax;
    }

    public void setNumberOfSpheres(Integer numberOfSpheres) {
        this.numberOfSpheres = numberOfSpheres;
    }

    public void setInterpolationMethod(InterpolationMethods interpolationMethod) {
        this.interpolationMethod = interpolationMethod;
    }


}
