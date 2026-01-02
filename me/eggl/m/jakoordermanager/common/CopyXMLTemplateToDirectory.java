/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 
 */
public interface CopyXMLTemplateToDirectory { 
    Path templatesDirectory = Path.of(".", "resources", "xmlTemplates");
}
