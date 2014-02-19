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
        //Gets list of points from DataModel
        ArrayList<Point3D> list = new DataModel().getListOfPoints();

        //Writes all points to console
        for(Point3D point3D: list) {
            System.out.println(point3D.toString());
        }
    }
}