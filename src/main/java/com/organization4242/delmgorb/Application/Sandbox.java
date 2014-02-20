package com.organization4242.delmgorb.Application;

import com.organization4242.delmgorb.Model.DataModel;
import com.organization4242.delmgorb.Model.Point3D;
import java.util.ArrayList;

/**
 * Sandbox
 *
 * Use this for debugging
 */
public class Sandbox {
    public static void main(String[] args) {
        int number_of_building_points = 100;
        ArrayList<Point3D> list_of_points;
        list_of_points = new DataModel(number_of_building_points).getListOfPoints();
        for (Point3D p: list_of_points)
            System.out.println(p.toString());
    }
}

