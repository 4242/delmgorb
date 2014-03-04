package com.organization4242.delmgorb.view;

import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 28.02.14.
 */
public class DialogWindowView extends JDialog {
    private static final Insets DEFAULT_INSETS = new Insets(5,5,5,5);
    private static final int PROGRESS_BAR_MIN_VALUE = 0;
    private static final int PROGRESS_BAR_MAX_VALUE = 100;

    private JButton button;

    public JButton getButton() {
        return button;
    }

    private JProgressBar progressBar;

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public DialogWindowView(JFrame parent, String title, Boolean enableCancel) {
        super(parent, title);
        init(enableCancel);
        setLocationRelativeTo(parent);
    }

    private void init(Boolean enableCancel) {
        setSize(200, 100);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        //setAlwaysOnTop(true);

        button = new JButton("Cancel");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(PROGRESS_BAR_MIN_VALUE);
        progressBar.setMaximum(PROGRESS_BAR_MAX_VALUE);
        progressBar.setValue(PROGRESS_BAR_MIN_VALUE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = DEFAULT_INSETS;
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

        if (!enableCancel) {
            button.setEnabled(false);
        }
        add(button);
        add(progressBar);
    }

    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setModalityType(ModalityType.APPLICATION_MODAL);
                setVisible(true);
            }
        });
    }
}
