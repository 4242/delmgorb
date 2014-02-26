package com.organization4242.delmgorb.Application;

import com.organization4242.delmgorb.Model.DataModel;
import com.organization4242.delmgorb.Model.IntegrationMethods;
import com.organization4242.delmgorb.Model.Point3D;
import com.organization4242.delmgorb.Model.ThreeArrays;

import java.util.ArrayList;

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

        //ArrayList<Point3D> list_of_points;
        //list_of_points = new DataModel(number_of_building_points,
        //        time_step_of_integration, method).getListOfPoints();

        ThreeArrays triple_array;
        triple_array = new DataModel(number_of_building_points,
                time_step_of_integration, method, 1, 2, 0.05, 1).getThreeArrays();

        //for (Point3D p: list_of_points)
        //    System.out.println(p.toString());
    }
}

