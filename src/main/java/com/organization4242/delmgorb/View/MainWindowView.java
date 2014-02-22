package com.organization4242.delmgorb.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ilya-murzinov on 22.02.14.
 */
public class MainWindowView extends JPanel{
    private JFrame jf;
    private JTextField textField;
    private JButton button;

    public JTextField getTextField() {
        return textField;
    }

    public JButton getButton() {
        return button;
    }

    public MainWindowView() {
        jf = new JFrame("Main Window");
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception e){
        }
        jf.setSize(400, 300);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setLayout(new GridLayout(2, 1));
        button = new JButton("Draw!");
        textField = new JTextField();
        jf.add(textField);
        jf.add(button);
    }

    public void display() {
        jf.setVisible(true);
    }
}
