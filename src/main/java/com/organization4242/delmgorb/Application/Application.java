package com.organization4242.delmgorb.Application;

import com.organization4242.delmgorb.Controller.MainWindowController;
import com.organization4242.delmgorb.Model.MainWindowModel;
import com.organization4242.delmgorb.View.MainWindowView;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        MainWindowView view = new MainWindowView();
        new MainWindowController(view, new MainWindowModel());
        view.display();
    }
}
