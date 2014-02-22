package com.organization4242.delmgorb.Model;

import net.ericaro.surfaceplotter.DefaultSurfaceModel;
import net.ericaro.surfaceplotter.Mapper;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class GraphModel {
    private DefaultSurfaceModel model;

    public DefaultSurfaceModel getModel() {
        return model;
    }

    public GraphModel() {
        model = new DefaultSurfaceModel();
        model.setMapper(new Mapper() {
            @Override
            public float f1(float x, float y) {
                return (float)(Math.sin(Math.pow(x,3)) + Math.pow(y,2));
            }

            @Override
            public float f2(float x, float y) {
                return 0;
            }
        });
    }
}
