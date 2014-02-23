package com.organization4242.delmgorb.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowView extends JPanel{
    private JFrame jf;
    private JLabel label;
    private int textFieldNumber = 14;
    private JTextField[] textFields = new JTextField[textFieldNumber];
    private JComboBox<IntegrationMethods> comboBox;
    private JButton button;

    public JTextField[] getTextFields() {
        return textFields;
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
        jf.setSize(400, 160);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        label = new JLabel("Input parameters:");
        for (int i=0; i<textFieldNumber; i++) {
            textFields[i] = new JTextField(4);
            textFields[i].setText("0");
        }
        button = new JButton("Draw!");
        comboBox = new JComboBox<IntegrationMethods>(IntegrationMethods.values());
        comboBox.setEditable(false);
        //comboBox.
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
        c.gridwidth = 3;
        c.gridx = 0;
        gbl.setConstraints(comboBox, c);
        jf.add(comboBox);
        c.gridx = 5;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        gbl.setConstraints(button, c);
        jf.add(button);
    }

    public void display() {
        jf.setVisible(true);
    }
}

enum IntegrationMethods {
    Euler,
    Midpoint,
    ClassicalRungeKutta,
    Gill,
    ThreeEights,
    HighamAndHall,
    DormandPrince5,
    DormandPrince8,
    GraggBulirschStoer,
    AdamsBashforth,
    AdamsMoulton,

}
