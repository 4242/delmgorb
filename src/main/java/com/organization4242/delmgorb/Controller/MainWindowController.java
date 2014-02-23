package com.organization4242.delmgorb.Controller;

import com.organization4242.delmgorb.Model.GraphModel;
import com.organization4242.delmgorb.Model.MainWindowModel;
import com.organization4242.delmgorb.View.GraphView;
import com.organization4242.delmgorb.View.GraphWindowView;
import com.organization4242.delmgorb.View.MainWindowView;

import javax.swing.*;
import java.awt.event.*;

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
            Boolean canDraw = true;
            for (JTextField tf : view.getTextFields()) {
                if (!tf.getText().equals("")) {
                    System.out.println(tf.getText());
                }
                else {
                    canDraw = false;
                    JOptionPane.showMessageDialog(view, "Please fill all fields");
                }
            }
            if (!graphWindowOpened && canDraw) {
                GraphWindowView view = new GraphWindowView(new GraphView(new GraphModel()));
                view.addWindowListener(windowListener);
                view.display();
            }
        }
    };

    //TODO: not working!
    private WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
            graphWindowOpened = true;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            graphWindowOpened = false;
        }
    };
}
