/**
 * 
 */
package me.eggl.m.jakoordermanager.core;

import static me.eggl.m.jakoordermanager.core.JOMTemplateDirectory.XML_TEMPLATE_DIRECTORY;

import java.io.IOException;
import java.nio.file.Path;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

import org.jdom2.Document;
import org.jdom2.JDOMException;

import me.eggl.m.jakoordermanager.common.FileHandler;
import me.eggl.m.jakoordermanager.common.GetSpecials;
import me.eggl.m.jakoordermanager.common.XMLFileHandler;
import me.eggl.m.jakoordermanager.ui.Chooser;
import me.eggl.m.jakoordermanager.ui.UiDialogs;

/**
 * The class is the connection between the app and OMWorkingDirectory.xml
 * 
 * The setter and getter validate the working directory. If the working
 * directory is not valid, the user has to choose a new valid directory.
 * For the validation it would be checked if the directory exists and
 * the user has the read and write permissions for it.
 * A new valid set working directory would also be saved in the xml-file and
 * in the class parameter.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class JOMWorkingDirectory {
    
    private static final Logger LOGGER = Logger.getLogger(JOMWorkingDirectory.class.getName());
    private static JOMWorkingDirectory instance = new JOMWorkingDirectory();
    
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false);
    }
    
    private final String workingDirectoryFilename = "JOMWorkingDirectory.xml";
    private String workingDirectory;
    private Document doc;
    

    /**
     * The constructor get the working directory from a 
     * XML-file and check if it is valid.
     * 
     * In case of a invalid working directory it would be reset.
     * Private constructor to prevent instantiation from outside.
     */
    private JOMWorkingDirectory() {
        super();
        try {
            Path source = Path.of(XML_TEMPLATE_DIRECTORY.toString(), workingDirectoryFilename);
            Path target = Path.of(".", workingDirectoryFilename);
            
            if ( ! FileHandler.checkIfFileExists(target) ) {
                LOGGER.log(Level.INFO, 
                        "File {0} not exists", 
                        GetSpecials.setTextColorForTerminal(target.toString(), "g"));
                XMLFileHandler.copyXMLTemplateInDirectory(source, target);
            }
            if ( ! FileHandler.checkReadWritePermissionsForExistingPath(target) ) {
                UiDialogs.appExitWithMessage( String.format("No read-/write-Permissions for %s !", target) );
            }
            this.doc = XMLFileHandler.readXMLObjectFromFile(target.toString());
            this.workingDirectory = this.getValidWorkingDirectoryOrExit();
            LOGGER.log(Level.FINE, "Arbeitsverzeichnis: {0}", 
                FileHandler.checkIfDirectoryExists(this.workingDirectory));
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
            UiDialogs.appExitWithMessage("Exception:" + "\n" + e.getMessage() + "\n" + e.getCause());
        }
    }
    
    public static JOMWorkingDirectory getInstance() {
        return instance;
    }

    /**
     * The method show a dialog for choosing a new working directory.
     * 
     * The user has to choose a valid directory, or the app would be finished. 
     * 
     * @throws IOException if there a problems with the directory.
     */
    private void chooseWorkingDirectoryOrExit() throws IOException {
        this.setNewWorkingDirectory(Chooser.directoryChooser("Choose working directory"));
        LOGGER.log(Level.FINE, "Arbeitsverzeichnis: {0}", this.workingDirectory);
        if ( this.getWorkingDirectory() == null || this.getWorkingDirectory().isBlank() ) {
            UiDialogs.appExitWithMessage("No valid working directory choosen!");
        }
    }
    
    /**
     * 
     */
    public void changeWorkingDirectory() {
        try {
            this.setNewWorkingDirectory(Chooser.directoryChooser("Choose working directory"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.log(Level.FINE, "Choosed Working Directory: {0}", this.workingDirectory);
    }
    
    /**
     * @return the workingDirectory
     */
    public String getWorkingDirectory() {
        return this.workingDirectory;
    }
    

    /**
     * The method set a new working directory.
     * 
     * The working directory will be checked before setting.
     * If the new one is not valid, it will not be set. 
     * If the current working directory is valid, the method
     * will be finished without chances.
     * If the current working directory is also not valid,
     * the app will be finished. 
     * 
     * @param newWorkingDirectory the workingDirectory to set
     * @throws IOException there a problems with the directory
     */
    private void setNewWorkingDirectory(String newWorkingDirectory) throws IOException {
        newWorkingDirectory.strip();
        LOGGER.log(Level.FINE, "Arbeitsverzeichnis (strip): {0}", newWorkingDirectory);
        if ( FileHandler.checkDirectoryExistsAndPermissions( newWorkingDirectory ) ) {
            saveNewWorkingDirectory(newWorkingDirectory);
            return;
        }
        if ( FileHandler.checkDirectoryExistsAndPermissions(workingDirectory) ) {
            UiDialogs.appMessage(String.format("The working directory had not changed.\nThe current working directory: %s", 
                                                workingDirectory ) );
            return;
        }
        UiDialogs.appExitWithMessage("No valid working directory set.");
    }

    /**
     * The method save the working directory to the internal parameter and also
     * to the XML-file. 
     * 
     * @param workingDirectory to save
     */
    private void saveNewWorkingDirectory(String workingDirectory) {
        LOGGER.log(Level.FINE, "SetWorkingDirectotry");
        this.workingDirectory = workingDirectory;
        this.doc.getRootElement().setText(workingDirectory);
        this.writeToXMLFile();
        LOGGER.log(Level.INFO, 
                "Arbeitsverzeichnis {0} eingetragen", 
                GetSpecials.setTextColorForTerminal(workingDirectory, "g"));
    }
    
    
    /**
     * The method get a valid working directory or finishes the app.
     * 
     * Firstly it would be looked in the XML-file.
     * If this directory is not useable, the user has to choose
     * a new one.
     * 
     * @throws IOException
     */
    private String getValidWorkingDirectoryOrExit() throws IOException {
        String pathFromXMLFile = this.doc.getRootElement().getText();
        if ( ! FileHandler.checkDirectoryExistsAndPermissions(pathFromXMLFile)) {
            LOGGER.log(Level.FINE, "current working directory is not valid");
            this.chooseWorkingDirectoryOrExit();
            pathFromXMLFile = this.doc.getRootElement().getText();
        }
        return pathFromXMLFile;
    }
    
    /**
     * Write the XML-object to file.
     * 
     * In case of a IOException, the app would be finished.
     */
    private void writeToXMLFile() {
        try {
            XMLFileHandler.writeXMLObjectToFile(doc, workingDirectoryFilename);
        } catch (IOException e) {
            e.printStackTrace();
            UiDialogs.appExitWithMessage("Exception:" + "\n" + e.getMessage() + "\n" + e.getCause());
        }
    }
}












