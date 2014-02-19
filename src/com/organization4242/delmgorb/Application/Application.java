package com.organization4242.delmgorb.Application;

import com.organization4242.delmgorb.Controller.GraphController;
import com.organization4242.delmgorb.Model.GraphModel;
import com.organization4242.delmgorb.View.GraphView;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class Application {
    public static void main(String[] args) {
        GraphView view = new GraphView();
        new GraphController(new GraphModel(), view);
        view.display();
    }
}
