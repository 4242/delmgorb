package com.organization4242.delmgorb.Model;

import net.ericaro.surfaceplotter.DefaultSurfaceModel;
import net.ericaro.surfaceplotter.Mapper;
import org.apache.commons.math3.analysis.MultivariateFunction;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class GraphModel {
    private DefaultSurfaceModel model;

    public DefaultSurfaceModel getModel() {
        return model;
    }

    public GraphModel(final MultivariateFunction function) {
        model = new DefaultSurfaceModel();
        model.setXMax((float) 2.0);
        model.setXMin((float) 1.0);
        model.setYMax((float) 1.0);
        model.setYMin((float) 0.0);

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
