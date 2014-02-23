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

    public MainWindowController(MainWindowView view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        for (JTextField tf : view.getTextFields()) {
            tf.addFocusListener(focusListener);
        }
        view.getButton().addMouseListener(mouseListener);
        view.getComboBox().addItemListener(itemListener);
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

    private ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED)
                System.out.println(((JComboBox) e.getSource()).getSelectedItem());
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

    private FocusListener focusListener = new FocusAdapter() {
        @Override
        public void focusGained(final FocusEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JTextField tf = (JTextField) e.getComponent();
                    tf.selectAll();
                }
            });
        }
    };
}
