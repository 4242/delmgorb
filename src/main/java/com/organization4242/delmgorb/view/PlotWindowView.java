package com.organization4242.delmgorb.view;

import javax.swing.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class PlotWindowView extends JFrame {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    public PlotWindowView(PlotView view) {
        setTitle("Plot");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add(view);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setVisible(true);
        }
    };

    public void display() {
        SwingUtilities.invokeLater(runnable);
    }
}
