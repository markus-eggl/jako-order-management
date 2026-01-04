/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import javax.swing.JOptionPane;

/**
 * various JDialogs in static methods
 * 
 * This class provides only static helper methods 
 * and is not intended to be instantiated or extended.
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
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private UiDialogs() {};
    
    public static void appExitWithMessage(String message) {
        message = message + "\nOrder Manager would be closed!";
        JOptionPane.showMessageDialog(null, message);
        System.exit(0);
    }
    
    public static void appMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}














