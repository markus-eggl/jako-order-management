/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;

import javax.swing.JFileChooser;

/**
 * The class provide a directory chooser.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class Chooser {
    
    private static final Logger LOGGER = Logger.getLogger(Chooser.class.getName());
    
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
    }
    
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
