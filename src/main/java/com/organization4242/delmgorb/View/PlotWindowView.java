package com.organization4242.delmgorb.View;

import javax.swing.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class PlotWindowView extends JFrame {
    private PlotView view;

    public PlotWindowView(PlotView view) {
        this.view = view;
        setTitle("Plot");
        setSize(800, 600);
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
