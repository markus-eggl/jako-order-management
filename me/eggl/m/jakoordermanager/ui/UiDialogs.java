/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import javax.swing.JOptionPane;

/**
 * 
 */
public class UiDialogs {
    public static void appExitWithMessage(String message) {
        message = message + "\nProgramm wird beendet!";
        JOptionPane.showMessageDialog(null, message);
        System.exit(0);
    }
}
