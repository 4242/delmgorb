package com.organization4242.delmgorb.model;

import javax.swing.*;
import java.io.File;

/**
 * Util class for creating "Open file dialog".
 * Used for serialization/deserialization.
 *
 * @author Murzinov Ilya
 */
public final class OpenFileHelper {
    private static JFileChooser chooser = new JFileChooser();
    private static File file;

    private OpenFileHelper() {

    }

    /**
     * Creates "Open file" dialog.
     *
     * @param view parent view.
     * @return chosen file.
     */
    public static File open(JFrame view) {
        int fileChosen = chooser.showDialog(view, "Open file");
        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        return file;
    }
}
