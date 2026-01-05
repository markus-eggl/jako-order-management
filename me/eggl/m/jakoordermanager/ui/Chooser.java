/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;

import javax.swing.JFileChooser;

/**
 * The utility class provide a directory chooser.
 * 
 * This class provides only static helper methods 
 * and is not intended to be instantiated or extended.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public final class Chooser {
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private Chooser() {};
    
    private static final Logger LOGGER = Logger.getLogger(Chooser.class.getName());
    
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
    }
    
    /**
     * This is a file chooser for directories.
     * 
     * @param title the title of the dialog.
     * @return the chosen directory or "" if no directory was chosen.
     */
    public static String directoryChooser(String title) {
        LOGGER.log(Level.INFO, "directoryChooser: {0}", title);
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showDialog(null, "Choose");
        if (result == JFileChooser.APPROVE_OPTION) {
            LOGGER.log(Level.FINE, "Ausgewälter Ordner: {0}", chooser.getSelectedFile());
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }
}
