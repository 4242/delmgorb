package com.organization4242.delmgorb.view;

import com.organization4242.delmgorb.controller.DialogWindowController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * Represents dialog window with progress bar and cancel button.
 * It is used to display progress of long-going operations such as calculating
 * maximal values or interpolating set of points.
 * 
 * It is used to display progress of {@link com.organization4242.delmgorb.model.PlotBuilder} task.
 * Also it provides ability to interrupt whose operations.
 *
 * This dialog can not be closed directly, you need to assign custom action listener
 * to cancel button in order to close it.
 * 
 * @author Murzinov Ilya
 */
public class DialogWindowView extends AbstractView {
    private static final int HEIGHT = 100;
    private static final int WIDTH = 200;
    private static final Insets DEFAULT_INSETS = new Insets(5,5,5,5);
    private static final int PROGRESS_BAR_MIN_VALUE = 0;
    private static final int PROGRESS_BAR_MAX_VALUE = 100;

    private JDialog dialog;

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
    * If you use this constructor, cancel button will be disabled.
    *
    * @param title title of dialog window.
    */
    public DialogWindowView(String title) {
        dialog = new JDialog((JFrame) null, title);
        dialog.setResizable(false);
        dialog.setSize(200, 100);
        dialog.setLocation(MainWindowView.WIDTH/2, MainWindowView.HEIGHT/2 - dialog.getHeight()/2);
        init();
    }

    /**
    * Initializes dialog window with controls.
    */
    private void init() {
        dialog.setSize(WIDTH, HEIGHT);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        button = new JButton("Cancel");
        progressBar = new JProgressBar();
        textArea = new JLabel("Calculating");
        progressBar.setStringPainted(true);
        progressBar.setMinimum(PROGRESS_BAR_MIN_VALUE);
        progressBar.setMaximum(PROGRESS_BAR_MAX_VALUE);
        progressBar.setValue(PROGRESS_BAR_MIN_VALUE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        dialog.setLayout(gridBagLayout);

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

        dialog.add(button);
        dialog.add(progressBar);
        dialog.add(textArea);
    }

    /**
    * Shows dialog using {@code SwingUtilities.invokeLater()}.
    */
    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialog.setVisible(true);
            }
        });
    }

    public void close() {
        progressBar.setValue(0);
        dialog.setVisible(false);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case DialogWindowController.INIT : {
                display();
                break;
            } case DialogWindowController.PERCENTAGE : {
                progressBar.setValue((Integer) pce.getNewValue());
                break;
            } case DialogWindowController.DISPOSE : {
                dialog.dispose();
                break;
            } case DialogWindowController.CALCULATED : {
                textArea.setText("Drawing");
                button.setEnabled(false);
                break;
            }
        }
    }
}
