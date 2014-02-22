package com.organization4242.delmgorb.Application;

import com.organization4242.delmgorb.Model.GraphModel;
import com.organization4242.delmgorb.View.GraphView;
import com.organization4242.delmgorb.View.GraphWindowView;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        new GraphWindowView(new GraphView(new GraphModel())).display();
    }
}
