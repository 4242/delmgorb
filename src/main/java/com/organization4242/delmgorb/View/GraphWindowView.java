package com.organization4242.delmgorb.View;

import javax.swing.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class GraphWindowView extends JFrame{
    private GraphView view;

    public GraphWindowView(GraphView view) {
        this.view = view;
    }

    public void display() {
        JFrame jf = new JFrame("Plot");
        jf.setSize(800, 600);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.getContentPane().add(view);
        jf.setVisible(true);
    }
}
