package com.organization4242.delmgorb.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 28.02.14.
 */
public class DialogWindowView extends JDialog {
    private JButton button;

    public JButton getButton() {
        return button;
    }

    private JProgressBar progressBar;

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public DialogWindowView(JPanel parent) {
        init();
        setLocationRelativeTo(parent);
    }

    public DialogWindowView(JFrame parent) {
        init();
        setLocationRelativeTo(parent);
    }

    private void init() {
        setSize(200, 100);
        setTitle("Computing..");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        button = new JButton("Cancel");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        gridBagLayout.setConstraints(progressBar, constraints);
        progressBar.setIndeterminate(false);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy++;
        gridBagLayout.setConstraints(button, constraints);

        add(button);
        add(progressBar);
    }

    public void display() {
        setVisible(true);
    }
}
