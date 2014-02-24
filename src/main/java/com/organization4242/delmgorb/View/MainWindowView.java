package com.organization4242.delmgorb.View;

import com.organization4242.delmgorb.Model.IntegrationMethods;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowView extends JPanel{
    private JFrame jf;
    private int textFieldNumber = 14;
    private JTextField[] textFields = new JTextField[textFieldNumber];
    private JTextField numberOfPointsTextField;
    private JTextField timeStepTextField;
    private JComboBox<IntegrationMethods> comboBox;
    private JButton button;

    public JTextField[] getTextFields() {
        return textFields;
    }

    public JTextField getNumberOfPoints() {
        return numberOfPointsTextField;
    }

    public JTextField getTimeStep() {
        return timeStepTextField;
    }

    public JButton getButton() {
        return button;
    }

    public JComboBox<IntegrationMethods> getComboBox() {
        return comboBox;
    }

    public MainWindowView() {
        jf = new JFrame("Main Window");
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception e){
        }
        jf.setSize(400, 185);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel("Input parameters:");
        JLabel numLabel = new JLabel("Number of points:");
        JLabel timeLabel = new JLabel("Time step:");
        for (int i=0; i<textFieldNumber; i++) {
            textFields[i] = new JTextField(4);
            textFields[i].setText("0");
        }
        numberOfPointsTextField = new JTextField(4);
        numberOfPointsTextField.setText("4");
        timeStepTextField = new JTextField(4);
        timeStepTextField.setText("0.5");
        button = new JButton("Draw!");
        comboBox = new JComboBox<IntegrationMethods>(IntegrationMethods.values());
        comboBox.setEditable(false);
        GridBagLayout gbl = new GridBagLayout();
        jf.setLayout(gbl);
        GridBagConstraints c =  new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill   = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = textFieldNumber/2;
        gbl.setConstraints(label, c);
        jf.add(label);
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
        c.gridx = 0;
        c.gridwidth = 3;
        gbl.setConstraints(numLabel, c);
        jf.add(numLabel);
        c.gridx = 2;
        gbl.setConstraints(numberOfPointsTextField, c);
        jf.add(numberOfPointsTextField);
        c.gridx = 4;
        c.gridwidth = 2;
        gbl.setConstraints(timeLabel, c);
        jf.add(timeLabel);
        c.gridx = 6;
        gbl.setConstraints(timeStepTextField, c);
        jf.add(timeStepTextField);
        c.gridy++;
        c.gridwidth = 4;
        c.gridx = 0;
        c.insets = new Insets(10, 0, 5, 0);
        gbl.setConstraints(comboBox, c);
        jf.add(comboBox);
        c.gridx = 5;
        c.gridwidth = 2;
        gbl.setConstraints(button, c);
        jf.add(button);
    }

    public void display() {
        jf.setVisible(true);
    }
}
