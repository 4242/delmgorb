package com.organization4242.delmgorb.model;

import net.ericaro.surfaceplotter.DefaultSurfaceModel;
import net.ericaro.surfaceplotter.Mapper;
import org.apache.commons.math3.analysis.MultivariateFunction;

/**
 * Represents the function created by {@link com.organization4242.delmgorb.model.DataModel}.
 * Used to pass to {@link com.organization4242.delmgorb.view.PlotView}
 *
 * @author Murzinov Ilya
 */
public class PlotModel {
    private DefaultSurfaceModel model;

    public DefaultSurfaceModel getModel() {
        return model;
    }

    public PlotModel(final MultivariateFunction function,
                     float xMin, float xMax, float yMin, float yMax) {
        model = new DefaultSurfaceModel();
        model.setXMax(xMax);
        model.setXMin(xMin);
        model.setYMax(yMax);
        model.setYMin(yMin);

        model.setBoxed(true);
        model.setDisplayXY(true);
        model.setDisplayZ(true);
        model.setDisplayGrids(true);

        model.setMapper(new Mapper() {
            @Override
            public float f1(float x, float y) {
                return (float) function.value(new double[]{x, y});
            }

            @Override
            public float f2(float x, float y) {
                return 0;
            }
        });
    }
}
