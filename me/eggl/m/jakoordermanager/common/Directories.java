/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This utility class provide the various directories
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
public final class Directories {
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private Directories() {}
    
    public static final Path XML_TEMPLATE_DIRECTORY = Path.of(".", "resources", "xmlTemplates");
    public static final List<Path> SUB_DIRECTORIES = new ArrayList<>() {
        
    };
    
}








