package com.organization4242.delmgorb.Application;

import com.organization4242.delmgorb.Model.DataModel;
import com.organization4242.delmgorb.Model.IntegrationMethods;
import com.organization4242.delmgorb.Model.PointsArray;

/**
 * Sandbox
 *
 * Use this for debugging
 */
public class Sandbox {
    public static void main(String[] args) {
        int number_of_building_points = 4;
        double time_step_of_integration = 0.5;
        IntegrationMethods method = IntegrationMethods.DormandPrince8;

        //list_of_points = new DataModel(number_of_building_points,
        //        time_step_of_integration, method).getListOfPoints();

        PointsArray triple_array;
        triple_array = new DataModel(number_of_building_points,
                time_step_of_integration, method, 1, 2, 0.05, 1).getPointsArray();
    }
}

