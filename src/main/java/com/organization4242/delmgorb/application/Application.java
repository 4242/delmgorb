package com.organization4242.delmgorb.application;

import com.organization4242.delmgorb.controller.MainWindowController;
import com.organization4242.delmgorb.model.MainWindowModel;
import com.organization4242.delmgorb.view.MainWindowView;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public final class Application {
    private Application() {

    }

    public static void main(String[] args) {
        MainWindowView view = new MainWindowView();
        new MainWindowController(view, new MainWindowModel());
        view.display();
    }
}
