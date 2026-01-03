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
        message = message + "\nOrder Manager would be closed!";
        JOptionPane.showMessageDialog(null, message);
        System.exit(0);
    }
    
    public static void appMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}














