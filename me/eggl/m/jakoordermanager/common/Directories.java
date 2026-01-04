/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.nio.file.Path;

/**
 * Provide various the directories
 * 
 * The class is public final and provide the following constants:
 * - XML_TEMPLATE_DIRECTORY: XML templates and additional necessary files
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public final class Directories {
    private Directories() {}
    public static final Path XML_TEMPLATE_DIRECTORY = Path.of(".", "resources", "xmlTemplates");
}
