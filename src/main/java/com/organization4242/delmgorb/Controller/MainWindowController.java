package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.GraphModel;
import com.organization4242.delmgorb.Model.MainWindowModel;
import com.organization4242.delmgorb.View.GraphView;
import com.organization4242.delmgorb.View.GraphWindowView;
import com.organization4242.delmgorb.View.MainWindowView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowController {
    private MainWindowModel model;
    private MainWindowView view;

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        view.getButton().addMouseListener(mouseListener);
    }

    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(view.getTextField().getText());
            new GraphWindowView(new GraphView(new GraphModel())).display();
        }
    };
}
