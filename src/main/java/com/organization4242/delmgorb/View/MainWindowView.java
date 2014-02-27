package com.organization4242.delmgorb.View;

import com.organization4242.delmgorb.Model.IntegrationMethods;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowView extends JPanel{
    //Window
    private JFrame jf = new JFrame("Delmgorb v1.0");

    //Labels
    private JLabel label = new JLabel("Input parameters:");
    private JLabel numLabel = new JLabel("Number of points:");
    private JLabel timeLabel = new JLabel("Time step:");
    private JLabel boundsLabel = new JLabel("Bounds:");
    private JLabel xLabel = new JLabel("delta = ");
    private JLabel yLabel = new JLabel("epsilon = ");
    private JLabel xToLabel = new JLabel("...");
    private JLabel yToLabel = new JLabel("...");
    private JLabel numberOfSpheresLabel = new JLabel("Number of spheres:");
    private JLabel brightnessLabel = new JLabel("Brightness:");

    //UI Controls
    private int textFieldNumber = 14;
    private JTextField[] textFields = new JTextField[textFieldNumber];
    private JTextField[] boundsTextFields = new JTextField[4];
    private JTextField numberOfPointsTextField;
    private JTextField timeStepTextField;
    private JComboBox<IntegrationMethods> comboBox;
    private JTextField numberOfSpheresTextField;
    private JTextField brightnessTextField;
    private JProgressBar progressBar;
    private JButton button;

    //Getters
    public JTextField[] getTextFields() {
        return textFields;
    }

    public JTextField[] getBoundsTextFields() {
        return boundsTextFields;
    }

    public JTextField getNumberOfPoints() {
        return numberOfPointsTextField;
    }

    public JTextField getTimeStep() {
        return timeStepTextField;
    }

    public JTextField getNumberOfSpheresTextField() {
        return numberOfSpheresTextField;
    }

    public JButton getButton() {
        return button;
    }

    public JComboBox<IntegrationMethods> getComboBox() {
        return comboBox;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public MainWindowView() {
        //Setting window parameters
        jf.setSize(400, 400);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Setting theme
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception e){
            System.out.println(e);
        }

        init();

        placeControls();
    }

    /**
     * Initializes all controls in the window
     */
    private void init() {

        for (int i=0; i<textFieldNumber; i++) {
            textFields[i] = new JTextField(4);
            textFields[i].setText("0");
        }
        for (int i=0; i<4; i++) {
            boundsTextFields[i] = new JTextField(4);
        }
        boundsTextFields[0].setText("1");
        boundsTextFields[1].setText("2");
        boundsTextFields[2].setText("0.05");
        boundsTextFields[3].setText("1");

        numberOfPointsTextField = new JTextField(4);
        numberOfPointsTextField.setText("4");
        timeStepTextField = new JTextField(4);
        timeStepTextField.setText("0.5");
        numberOfSpheresTextField = new JTextField(4);
        numberOfSpheresTextField.setText("100");
        brightnessTextField = new JTextField(4);
        brightnessTextField.setText("1");

        button = new JButton("Draw!");
        comboBox = new JComboBox<IntegrationMethods>(IntegrationMethods.values());
        comboBox.setSelectedItem(IntegrationMethods.DormandPrince8);
        comboBox.setEditable(false);
        progressBar = new JProgressBar();
    }

    private void placeControls() {
        GridBagLayout gbl = new GridBagLayout();
        jf.setLayout(gbl);
        GridBagConstraints c =  new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill   = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.insets = new Insets(10, 5, 0, 0);
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = textFieldNumber/2;
        c.anchor = GridBagConstraints.WEST;
        gbl.setConstraints(label, c);
        jf.add(label);
        c.anchor = GridBagConstraints.CENTER;
        c.gridy++;
        c.gridwidth = 1;
        for (int i=0; i<textFieldNumber/2; i++) {
            gbl.setConstraints(textFields[i], c);
            jf.add(textFields[i]);
        }
        c.gridy++;
        for (int i=textFieldNumber/2; i<textFieldNumber; i++) {
            gbl.setConstraints(textFields[i], c);
            jf.add(textFields[i]);
        }

        c.gridy++;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(20, 5, 0, 0);
        c.gridx = 0;
        c.gridwidth = 2;
        gbl.setConstraints(boundsLabel, c);
        jf.add(boundsLabel);

        c.insets = new Insets(10, 5, 0, 0);
        c.gridy++;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        gbl.setConstraints(xLabel, c);
        jf.add(xLabel);
        c.gridx = 1;
        c.anchor = GridBagConstraints.CENTER;
        gbl.setConstraints(boundsTextFields[0], c);
        jf.add(boundsTextFields[0]);
        c.gridx = 2;
        gbl.setConstraints(xToLabel, c);
        jf.add(xToLabel);
        c.gridx = 3;
        gbl.setConstraints(boundsTextFields[1], c);
        jf.add(boundsTextFields[1]);

        c.anchor = GridBagConstraints.EAST;
        c.gridy++;
        c.gridx = 0;
        gbl.setConstraints(yLabel, c);
        jf.add(yLabel);
        c.gridx = 1;
        c.anchor = GridBagConstraints.CENTER;
        gbl.setConstraints(boundsTextFields[2], c);
        jf.add(boundsTextFields[2]);
        c.gridx = 2;
        gbl.setConstraints(yToLabel, c);
        jf.add(yToLabel);
        c.gridx = 3;
        gbl.setConstraints(boundsTextFields[3], c);
        jf.add(boundsTextFields[3]);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.SOUTHWEST;
        gbl.setConstraints(numLabel, c);
        jf.add(numLabel);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.insets = new Insets(20, 5, 0, 0);
        gbl.setConstraints(numberOfPointsTextField, c);
        jf.add(numberOfPointsTextField);
        c.gridy++;
        c.insets = new Insets(10, 5, 0, 0);
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.gridx = 0;
        c.gridwidth = 3;
        gbl.setConstraints(timeLabel, c);
        jf.add(timeLabel);
        c.gridx = 2;
        c.anchor = GridBagConstraints.CENTER;
        gbl.setConstraints(timeStepTextField, c);
        jf.add(timeStepTextField);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.SOUTHWEST;
        gbl.setConstraints(numberOfSpheresLabel, c);
        jf.add(numberOfSpheresLabel);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.insets = new Insets(20, 5, 0, 0);
        gbl.setConstraints(numberOfSpheresTextField, c);
        jf.add(numberOfSpheresTextField);
        c.gridy++;
        c.insets = new Insets(10, 5, 0, 0);
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.gridx = 0;
        c.gridwidth = 3;
        gbl.setConstraints(brightnessLabel, c);
        //jf.add(brightnessLabel);
        c.gridx = 2;
        c.anchor = GridBagConstraints.CENTER;
        gbl.setConstraints(brightnessTextField, c);
        //jf.add(brightnessTextField);

        c.gridy++;
        c.gridwidth = 4;
        c.gridx = 0;
        c.insets = new Insets(10, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        gbl.setConstraints(comboBox, c);
        jf.add(comboBox);
        c.gridx = 5;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.EAST;
        gbl.setConstraints(button, c);
        jf.add(button);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 7;
        gbl.setConstraints(progressBar, c);
        progressBar.setIndeterminate(false);
        jf.add(progressBar);
    }

    public void display() {
        jf.setVisible(true);
    }
}
