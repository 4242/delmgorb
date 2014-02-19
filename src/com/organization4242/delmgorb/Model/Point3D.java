package com.organization4242.delmgorb.Model;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class Point3D {
    double x;
    double y;
    double z;

    Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
