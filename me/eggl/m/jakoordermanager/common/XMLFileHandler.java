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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 
 */
public class XMLFileHandler implements XMLTemplateDirectory {
    
    private static final Logger LOGGER = Logger.getLogger(XMLFileHandler.class.getName());
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.FINE;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
    }
    
    public static Document readXMLObjectFromFile(String filepath) throws JDOMException, IOException {
        checkStringForNullOrEmpty(filepath);
        return new SAXBuilder().build(filepath);
    }
    
    public static void writeXMLObjectToFile(Document doc, String filepath) throws IOException {
        checkStringForNullOrEmpty(filepath);
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
    private static void checkStringForNullOrEmpty(String filepath) throws NoSuchFileException {
        if (filepath == null) {
            throw new NoSuchFileException("Der übergebene String ist null");
        }
        if (filepath.isBlank()) {
            throw new NoSuchFileException("Der übergebene String ist leer");
        }
    }
    
    
    public static void copyXMLTemplateInDirectory(Path source, Path target) throws IOException {
        LOGGER.log(Level.FINE, 
                "\t copyXMLTemplateInDirectory aufgerufen:\n\t source: {0},\n\t target: {1}.\t",
                new Object[] { source, target } );
        checkStringForNullOrEmpty(source.toString());
        checkStringForNullOrEmpty(target.toString());
        
        LOGGER.log(Level.FINE, 
                "{0} permissions: {1}.", 
                new Object[]  { GetSpecials.setTextColorForTerminal(source.toString(), "g"), 
                        FileHandler.checkFileExistsAndPermissions(source) } );
        
        getSourceFiles(source);
        
        if ( FileHandler.checkFileExistsAndPermissions(source) ) {
            LOGGER.log(Level.FINE, "Source {0} exists and read-/writeable.", source );
            LOGGER.log(Level.FINE, 
                    "{0} permissions: {1}.", 
                    new Object[]  { GetSpecials.setTextColorForTerminal(target.toString(), "g"), 
                            FileHandler.checkFileExistsAndPermissions(target) } );
            
            if ( ! FileHandler.checkFileExistsAndPermissions(target) ) {
                LOGGER.log(Level.FINE, "Target {0} exists and read-/writeable.", target );
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.log(Level.INFO, 
                        "XML Template {0} nach {1} kopiert.", 
                        new Object[] { GetSpecials.setTextColorForTerminal(source.toString(), "g"), 
                                GetSpecials.setTextColorForTerminal(target.toString(), "g") });
                return;
            }
        }
    }

    /**
     * @param source
     * @return
     */
    private static List<Path> getSourceFiles(Path source) {
        String filename = getFilenameWithoutExtension(source.toString());
        List<Path> pathlist = new ArrayList<>();
        
        try (Stream<Path> stream = Files.find(
                templateDirectory, 1, 
                (path, attr) -> path.toString().contains(filename) && attr.isRegularFile())) {
                    stream.forEach(pathlist::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.log(Level.FINE, "pathlist: {0}", GetSpecials.setTextColorForTerminal(pathlist.toString(), "g") );
        
        return null;
    }

    /**
     * @param source
     * @return
     */
    private static String getFilenameWithoutExtension(String source) {
        int pos = source.lastIndexOf('.');
        return pos >= 0 ? source.substring(0, pos) : source;
    }
    
}
