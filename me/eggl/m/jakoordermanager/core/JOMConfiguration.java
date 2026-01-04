/**
 * 
 */
package me.eggl.m.jakoordermanager.core;

import java.io.IOException;
import java.nio.file.Path;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

import org.jdom2.Document;
import org.jdom2.JDOMException;

import me.eggl.m.jakoordermanager.common.XMLTemplateDirectory;
import me.eggl.m.jakoordermanager.common.FileHandler;
import me.eggl.m.jakoordermanager.common.GetSpecials;
import me.eggl.m.jakoordermanager.common.XMLFileHandler;
import me.eggl.m.jakoordermanager.ui.Chooser;
import me.eggl.m.jakoordermanager.ui.UiDialogs;

/**
 * 
 */
public class JOMConfiguration implements XMLTemplateDirectory {
    
    private static final Logger LOGGER = Logger.getLogger(JOMConfiguration.class.getName());
    private static JOMConfiguration instance = new JOMConfiguration();
    
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
     * 
     */
    private JOMConfiguration() {
        super();
        try {
            Path source = Path.of(templateDirectory.toString(), workingDirectoryFilename);
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
    
    public static JOMConfiguration getInstance() {
        return instance;
    }

    /**
     * @throws IOException 
     * 
     */
    private void chooseWorkingDirectoryOrExit() throws IOException {
        this.setNewWorkingDirectory(Chooser.directoryChooser("Choose working directory"));
        LOGGER.log(Level.FINE, "Arbeitsverzeichnis: {0}", this.workingDirectory);
        if ( this.getWorkingDirectory() == null || this.getWorkingDirectory().isBlank() ) {
            UiDialogs.appExitWithMessage("No valid working directory choosen!");
        }
    }

    /**
     * @return the workingDirectory
     */
    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    /**
     * @param newWorkingDirectory the workingDirectory to set
     * @throws IOException 
     */
    public void setNewWorkingDirectory(String newWorkingDirectory) throws IOException {
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
     * @param workingDirectory
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
     * @throws IOException
     */
    private void resetAndChooseNewWorkingDirectory() throws IOException {
        String message = "The specified working directory cannot be used.\nPlease select a new one.";
        UiDialogs.appMessage(message);
        this.resetWorkingDirectory();
        this.chooseWorkingDirectoryOrExit();
    }
    
    private String getValidWorkingDirectoryOrExit() throws IOException {
        String pathFromXMLFile = this.doc.getRootElement().getText();
        if ( ! FileHandler.checkDirectoryExistsAndPermissions(pathFromXMLFile)) {
            LOGGER.log(Level.FINE, "current working directory is not valid");
            this.chooseWorkingDirectoryOrExit();
            pathFromXMLFile = this.doc.getRootElement().getText();
        }
        return pathFromXMLFile;
    }
    
    private void resetWorkingDirectory() {
        this.workingDirectory = "";
        this.doc.getRootElement().setText("");
        this.writeToXMLFile();
        LOGGER.log(Level.INFO, "Working directory was reset");
    }
    
    
    private void writeToXMLFile() {
        try {
            XMLFileHandler.writeXMLObjectToFile(doc, workingDirectoryFilename);
        } catch (IOException e) {
            e.printStackTrace();
            UiDialogs.appExitWithMessage("Exception:" + "\n" + e.getMessage() + "\n" + e.getCause());
        }
    }
}












