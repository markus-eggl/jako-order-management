/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import static me.eggl.m.jakoordermanager.core.JOMTemplateDirectory.XML_TEMPLATE_DIRECTORY;

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
 * This utility class provide static methods for XML-files.
 * 
 * This class provides only static helper methods 
 * and is not intended to be instantiated or extended.
 * 
 * There are methods for
 * - open XML-files and get the root object,
 * - save a XML object to a file
 * - validation of path elements
 * - looking for and copy template files to the working directory
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public final class XMLFileHandler {
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private XMLFileHandler() {};
    
    private static final Logger LOGGER = Logger.getLogger(XMLFileHandler.class.getName());
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false);
    }
    
    /**
     * This method read a XML-object from a file.
     * 
     * If the filepath is null or empty, it would be a 
     * NoSuchFileException thrown.
     * 
     * @param filepath Path to the (XML-)file
     * @return The XML-object from the file
     * @throws JDOMException if the data in the file is not valid for xml
     * @throws IOException if there a problems with the file or path
     */
    public static Document readXMLObjectFromFile(String filepath) throws JDOMException, IOException {
        checkStringForNullOrEmpty(filepath);
        return new SAXBuilder().build(filepath);
    }
    
    /**
     * This method write a XML-object to a file.
     * 
     * If filepath is null or empty, it would be an IOException thrown.
     * 
     * @param doc The XML-object for writing in the file.
     * @param filepath to the file that shout be written.
     * @throws IOException if the path is not valid or problems to writing the file.
     */
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
     * The method checks a string if it is null or empty.
     * 
     * @param filepath the string that should be tested.
     * @throws NoSuchFileException if the string is null or empty.
     */
    private static void checkStringForNullOrEmpty(String filepath) throws NoSuchFileException {
        if (filepath == null) {
            throw new NoSuchFileException("Der übergebene String ist null");
        }
        if (filepath.isBlank()) {
            throw new NoSuchFileException("Der übergebene String ist leer");
        }
    }
    
    /**
     * The method copy a file from source to target
     * 
     * @param source the file that should be copied.
     * @param target the point that should the file copy to. 
     * @throws IOException by problems with a Path or missing permissions.
     */
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
        
        LOGGER.log(Level.FINE, "FileList: {0}", 
                GetSpecials.setTextColorForTerminal(getSourceFiles(source).toString(), "g") );
        for ( Path sourceFile : getSourceFiles(source) ) {
            LOGGER.log(Level.INFO, "sourcefile: {0}", 
                    GetSpecials.setTextColorForTerminal(sourceFile.toString(), "g") );
            copyFile(getTargetFilePath(sourceFile, target), sourceFile);
        }
    }

    /**
     * helper to copy a file from source to target.
     * 
     * @param source the file that should be copied.
     * @param target the point that should the file copy to. 
     * @throws IOException by problems with a Path or missing permissions.
     */
    private static void copyFile(Path targetFile, Path sourceFile) throws IOException {
        if ( FileHandler.checkFileExistsAndPermissions(sourceFile) ) {
            LOGGER.log(Level.FINE, "Source {0} exists and read-/writeable.", 
                    GetSpecials.setTextColorForTerminal(sourceFile.toString(), "g") );
            
            LOGGER.log(Level.FINE, 
                    "{0} permissions: {1}.", 
                    new Object[]  { GetSpecials.setTextColorForTerminal(targetFile.toString(), "g"), 
                            FileHandler.checkFileExistsAndPermissions(targetFile) } );
            if ( ! FileHandler.checkFileExistsAndPermissions(targetFile) ) {
                LOGGER.log(Level.FINE, "Target {0} exists and read-/writeable.", targetFile );
                Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.log(Level.INFO, 
                        "XML Template {0} nach {1} kopiert.", 
                        new Object[] { GetSpecials.setTextColorForTerminal(sourceFile.toString(), "g"), 
                                GetSpecials.setTextColorForTerminal(targetFile.toString(), "g") });
            }
        }
    }

    /**
     * This method build the path with the name of the sourcefile
     * and the target directory.
     * 
     * The name of the file will not changed. 
     * 
     * @param sourceFile the name of the file
     * @param target the complete path with filename
     * @return the complete path for the target-file
     */
    private static Path getTargetFilePath(Path sourceFile, Path target) {
        return Path.of(target.getParent().toString() , sourceFile.getFileName().toString());
    }

    /**
     * The method looks for files with the same name in directory.
     * 
     * It would be looked for files with the same name but
     * different extensions in the same directory. 
     * For example the file "index.html":
     * If there a files like "index.css" and "index.js", all of this
     * filenames would be in the returned list.
     * 
     * @param source the specific path to the file
     * @return the list with all similar filenames but different extensions.
     */
    private static List<Path> getSourceFiles(Path source) {
        String filename = getFilenameWithoutExtension(source.toString());
        List<Path> pathlist = new ArrayList<>();
        
        try (Stream<Path> stream = Files.find(
                XML_TEMPLATE_DIRECTORY, 1, 
                (path, attr) -> path.toString().contains(filename) && attr.isRegularFile())) {
                    stream.forEach(pathlist::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        LOGGER.log(Level.FINE, "pathlist: {0}", GetSpecials.setTextColorForTerminal(pathlist.toString(), "g") );
        return pathlist;
    }

    /**
     * The method will return a string till the last '.'
     * 
     * If the String does not have a '.' or is null, it would be
     * returned the complete string.
     * 
     * @param source the string
     * @return the string to the last '.'
     */
    private static String getFilenameWithoutExtension(String source) {
        if ( source == null ) {
            return source;
        }
        int pos = source.lastIndexOf('.');
        return pos >= 0 ? source.substring(0, pos) : source;
    }
    
}
