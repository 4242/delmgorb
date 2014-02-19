package com.organization4242.delmgorb.View;

import org.sf.surfaceplot.SurfaceCanvas;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public class GraphView extends JFrame{

    private javax.swing.JPanel centerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;

    SurfaceCanvas canvas = new SurfaceCanvas();

    public SurfaceCanvas getCanvas() {
        return canvas;
    }

    public GraphView() {
        initComponents();

        setSize(800, 600);
    }

    public GraphView initCanvas() {
        centerPanel.add(canvas, BorderLayout.CENTER);
        canvas.repaint();
        setVisible(true);
        return this;
    }

    private void initComponents() {

        centerPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        centerPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        jLabel1.setText("Rotate: Mouse Click & Drag;");
        jPanel1.add(jLabel1);

        jLabel2.setText("Zoom: Shift Key + Mouse Click & Drag;");
        jPanel1.add(jLabel2);

        jLabel3.setText("Move: Control Key + Mouse Click & Drag;");
        jPanel1.add(jLabel3);

        centerPanel.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        pack();
    }

    public void display() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                initCanvas();
                setVisible(true);
            }
        });
    }
}
