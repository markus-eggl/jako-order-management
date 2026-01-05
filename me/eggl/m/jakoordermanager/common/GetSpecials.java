/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.awt.Insets;

/**
 * This utility class provide various static methods
 * 
 * This class provides only static helper methods 
 * and is not intended to be instantiated or extended.
 * 
 * - Insets for the JTextField
 * - colors for the terminal
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public final class GetSpecials {
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private GetSpecials() {};
    
    
    /**
     * the static methods returns a insets for JTextField.
     * 
     * The values can be changed here.
     * 
     * @return a insets for the border in a JTextField
     */
    public static Insets getInsetsForTextField() {
        // Insets(top, left, bottom, right)
        return new Insets(1, 3, 1, 3);
    }
    
    /**
     * The method set the text color for terminal.
     * 
     * The color information would be added before the text.
     * After the text the color would be reseted.
     * Colors:
     *  - 'r', 'red'
     *  - 'g', 'green'
     *  - 'y', 'yellow'
     *  - 'b', 'blue'
     *  - '' -> reset
     * 
     * @param text that should become a special color
     * @param color for the text
     * @return text with additional color-setting
     */
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
