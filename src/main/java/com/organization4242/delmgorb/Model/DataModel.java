package com.organization4242.delmgorb.Model;

import java.util.ArrayList;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class DataModel {
    private ArrayList<Point3D> listOfPoints = new ArrayList<Point3D>();

    public ArrayList<Point3D> getListOfPoints() {
        return listOfPoints;
    }

    public DataModel() {
        //Here listOfPoints gets assigned
        listOfPoints = YOUR_METHOD_NAME(/*add arguments*/);
    }

    //Rename this method
    private ArrayList<Point3D> YOUR_METHOD_NAME(/*add arguments*/) {
        ArrayList<Point3D> list = new ArrayList<Point3D>();

        //TODO: put your logic here

        return list;
    }
}
