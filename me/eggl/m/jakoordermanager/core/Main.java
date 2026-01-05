/**
 * 
 */
package me.eggl.m.jakoordermanager.core;

import me.eggl.m.jakoordermanager.ui.MainFrame;

/**
 * The main class for the app.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class Main {
    
    public static void main(String[] args) {
        JOMWorkingDirectory workingDirectory = JOMWorkingDirectory.getInstance();
        checkSubDirectories();
        
        new MainFrame(workingDirectory);
    }

    /**
     * 
     */
    private static void checkSubDirectories() {
        
        
    }
}
