package com.organization4242.delmgorb.application;

import com.organization4242.delmgorb.model.IntegrationMethods;
import com.organization4242.delmgorb.model.PointsArray;

/**
 * Sandbox
 *
 * Use this for debugging
 */
@SuppressWarnings("all")
public class Sandbox {
    public static void main(String[] args) {
        int numberOfBuildingPoints = 4;
        double timeStepOfIntegration = 0.5;
        IntegrationMethods method = IntegrationMethods.DORMAND_PRINCE_8;

        //list_of_points = new DataModel(numberOfBuildingPoints,
        //        timeStepOfIntegration, method).getListOfPoints();

        PointsArray tripleArray;
//        tripleArray = new DataModel(numberOfBuildingPoints, BuildingAngle.PSI, 100,
//                timeStepOfIntegration, 0.05, 0.05, 0.05, method, 1, 2, 0.05, 1).getPointsArray();
    }
}

