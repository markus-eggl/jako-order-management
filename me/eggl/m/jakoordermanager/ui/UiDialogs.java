/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import javax.swing.JOptionPane;

/**
 * various JDialogs in static methods
 * 
 * To inform the user about important processes in the app. If
 * a error occur, the app can also be closed after the
 * information.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class UiDialogs {
    
    public static void appExitWithMessage(String message) {
        message = message + "\nOrder Manager would be closed!";
        JOptionPane.showMessageDialog(null, message);
        System.exit(0);
    }
    
    public static void appMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}














