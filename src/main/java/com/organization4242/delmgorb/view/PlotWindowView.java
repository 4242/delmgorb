package com.organization4242.delmgorb.view;

import javax.swing.*;

/**
 * Represents window with resulting plot.
 *
 * @author Murzinov Ilya
 */
public class PlotWindowView extends JFrame {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    public PlotWindowView(PlotView view) {
        setTitle("Plot");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add(view);
        view.setFocusable(true);
        view.requestFocus();
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
