package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.GraphModel;
import com.organization4242.delmgorb.View.GraphView;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class GraphController {
    private GraphView view;
    private GraphModel model;

    public GraphController(GraphModel model, GraphView view) {
        this.view = view;
        this.model = model;
        view.getCanvas().setModel(model);
    }
}
