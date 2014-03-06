package com.organization4242.delmgorb.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class represents dialog window with progress bar and cancel button.
 * It is used to display progress of long-going operations such as calculating
 * maximal values or interpolating set of points.
 * Also it provides ability to interrupt whose operations.
 * 
 * By default cancel button doesn't have any action listener.
 * 
 * @author Murzinov Ilya
 */
public class DialogWindowView extends JDialog {
    private static final Insets DEFAULT_INSETS = new Insets(5,5,5,5);
    private static final int PROGRESS_BAR_MIN_VALUE = 0;
    private static final int PROGRESS_BAR_MAX_VALUE = 100;

    private JButton button;

    private JProgressBar progressBar;

    private JLabel textArea;
    
    public JButton getButton() {
        return button;
    }    

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getTextArea() {
        return textArea;
    }

    /**
    * Initializes dialog window without showing it.
    * 
    * @param parent - parent JFrame which creates dialog
    * @param title - title of dialog window
    */
    public DialogWindowView(JFrame parent, String title) {
        super(parent, title);
        init(false);
        setLocationRelativeTo(parent);
    }

    public DialogWindowView(JFrame parent, String title, ActionListener actionListener) {
        super(parent, title);
        init(true);
        button.addActionListener(actionListener);
        setLocationRelativeTo(parent);
    }

    private void init(Boolean enableCancel) {
        setSize(200, 100);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        //setAlwaysOnTop(true);

        button = new JButton("Cancel");
        progressBar = new JProgressBar();
        textArea = new JLabel();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(PROGRESS_BAR_MIN_VALUE);
        progressBar.setMaximum(PROGRESS_BAR_MAX_VALUE);
        progressBar.setValue(PROGRESS_BAR_MIN_VALUE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridwidth = 2;
        gridBagLayout.setConstraints(progressBar, constraints);
        progressBar.setIndeterminate(false);

        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridy++;
        gridBagLayout.setConstraints(textArea, constraints);

        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.fill = GridBagConstraints.NONE;
        gridBagLayout.setConstraints(button, constraints);

        if (!enableCancel) {
            button.setEnabled(false);
        }
        add(button);
        add(progressBar);
        add(textArea);
    }

    /**
    * Shows dialog.
    */
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
