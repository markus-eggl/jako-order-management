/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import javax.swing.JFileChooser;

/**
 * 
 */
public class Chooser {
    public static String directoryChooser(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showDialog(null, "Auswählen");
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }
}
