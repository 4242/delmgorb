package com.organization4242.delmgorb.utils;

import javax.swing.*;
import java.io.File;

/**
 * Created by ilya-murzinov on 02.03.14.
 */
public class OpenFileHelper {
    private static JFileChooser chooser = new JFileChooser();
    private static File file;

    public static File open(JFrame view) {
        int fileChosen = chooser.showDialog(view, "Open file");
        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            System.out.println("Chose");
        }
        return file;
    }
}
