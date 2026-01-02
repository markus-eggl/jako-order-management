/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 
 */
public class XMLFileHandler {
    
    private static final Logger LOGGER = Logger.getLogger(XMLFileHandler.class.getName());
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
    }
    
    public static Document readXMLObjectFromFile(String filepath) throws JDOMException, IOException {
        checkStringForNullAndEmpty(filepath);
        return new SAXBuilder().build(filepath);
    }
    
    public static void writeXMLObjectToFile(Document doc, String filepath) throws IOException {
        checkStringForNullAndEmpty(filepath);
        if (doc == null) {
            throw new IOException("Das übergebene XML-Objekt ist null");
        }
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try (FileWriter writer = new FileWriter(filepath)) {
            xmlOutput.output(doc, writer);
        }
    }


    /**
     * @param filepath
     * @throws NoSuchFileException
     */
    private static void checkStringForNullAndEmpty(String filepath) throws NoSuchFileException {
        if (filepath == null) {
            throw new NoSuchFileException("Der übergebene String ist null");
        }
        if (filepath.isBlank()) {
            throw new NoSuchFileException("Der übergebene String ist leer");
        }
    }
    
    
    public static void copyXMLTemplateInDirectory(Path source, Path target) throws IOException {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
    
}
