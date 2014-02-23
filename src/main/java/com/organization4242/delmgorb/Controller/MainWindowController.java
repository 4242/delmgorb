package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.GraphModel;
import com.organization4242.delmgorb.Model.MainWindowModel;
import com.organization4242.delmgorb.View.GraphView;
import com.organization4242.delmgorb.View.GraphWindowView;
import com.organization4242.delmgorb.View.MainWindowView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowController {
    private MainWindowModel model;
    private MainWindowView view;
    private Boolean graphWindowOpened = false;

    public void setGraphWindowOpened(Boolean graphWindowOpened) {
        this.graphWindowOpened = graphWindowOpened;
    }

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        view.getButton().addMouseListener(mouseListener);
    }

    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (JTextField tf : view.getTextFields())
                System.out.println(tf.getText());
            if (!graphWindowOpened) {
                GraphWindowView view = new GraphWindowView(new GraphView(new GraphModel()));
                view.display();
            }
        }
    };
}
