package com.organization4242.delmgorb.Model;

import org.sf.surfaceplot.ISurfacePlotModel;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class GraphModel implements ISurfacePlotModel
{
    public float calculateZ(float x, float y) {
        return (float)(Math.pow(x, 2) / Math.pow(y, 2));
    }

    public int getPlotMode()
    {
        return ISurfacePlotModel.PLOT_MODE_SPECTRUM;
    }

    public boolean isBoxed()
    {
        return true;
    }

    public boolean isMesh()
    {
        return true;
    }

    public boolean isScaleBox()
    {
        return false;
    }

    public boolean isDisplayXY()
    {
        return true;
    }

    public boolean isDisplayZ()
    {
        return true;
    }

    public boolean isDisplayGrids()
    {
        return true;
    }

    public int getCalcDivisions()
    {
        return 100;
    }

    public int getDispDivisions()
    {
        return 100;
    }

    public float getXMin()
    {
        return -15;
    }

    public float getXMax()
    {
        return 15;
    }

    public float getYMin()
    {
        return -15;
    }

    public float getYMax()
    {
        return 15;
    }

    public float getZMin()
    {
        return -1;
    }

    public float getZMax()
    {
        return 15;
    }

    public String getXAxisLabel()
    {
        return "X";
    }

    public String getYAxisLabel()
    {
        return "Y";
    }

    public String getZAxisLabel()
    {
        return "Z";
    }
}
