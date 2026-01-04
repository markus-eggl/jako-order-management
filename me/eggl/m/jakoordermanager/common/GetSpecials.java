/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.awt.Insets;

/**
 * various methods
 * 
 * - Insets for the JTextField
 * - colors for the terminal
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class GetSpecials {
    
    public static Insets getInsetsForTextField() {
        // Insets(top, left, bottom, right)
        return new Insets(1, 3, 1, 3);
    }
    
    public static String setTextColorForTerminal(String text, String color) {
        String choosedColor = ( switch (color) {
            case "r", "red" -> "\u001B[31m";
            case "g", "green"-> "\u001B[32m";
            case "y", "yellow" -> "\u001B[33m";
            case "b", "blue" -> "\u001B[34m";
            default -> "\u001B[0m";
        } );
        return  choosedColor + text + "\u001B[0m";
    }
}
