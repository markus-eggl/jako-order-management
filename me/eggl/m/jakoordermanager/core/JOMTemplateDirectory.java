/**
 * 
 */
package me.eggl.m.jakoordermanager.core;

import java.nio.file.Path;

/**
 * This utility class provide the directory for the xml templates
 * 
 * This class provides only static constants 
 * and is not intended to be instantiated or extended.
 * 
 * The class is public final and provide the following constants:
 * - XML_TEMPLATE_DIRECTORY: XML templates and additional necessary files
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public final class JOMTemplateDirectory {
    
    public static final Path XML_TEMPLATE_DIRECTORY = Path.of(".", "resources", "xmlTemplates");
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private JOMTemplateDirectory() {}

}








