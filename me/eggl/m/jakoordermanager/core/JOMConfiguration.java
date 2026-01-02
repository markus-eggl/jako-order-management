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

import me.eggl.m.jakoordermanager.common.CopyXMLTemplateToDirectory;
import me.eggl.m.jakoordermanager.common.FileHandler;
import me.eggl.m.jakoordermanager.common.XMLFileHandler;
import me.eggl.m.jakoordermanager.ui.Chooser;
import me.eggl.m.jakoordermanager.ui.UiDialogs;

/**
 * 
 */
public class JOMConfiguration implements CopyXMLTemplateToDirectory {
    
    private static final Logger LOGGER = Logger.getLogger(JOMConfiguration.class.getName());
    
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
    }
    
    private final String workingDirectoryFilename = "JOMWorkingDirectory.xml";
    private String workingDirectory;
    private Document doc;
    
    /**
     * 
     */
    public JOMConfiguration() { // TODO convert to a singelton
        super();
        try {
            Path source = Path.of(templatesDirectory.toString(), workingDirectoryFilename);
            Path target = Path.of(".", workingDirectoryFilename);
            
            if ( ! FileHandler.checkIfFileExists(target) ) {
                XMLFileHandler.copyXMLTemplateInDirectory(source, target);
            }
            if ( FileHandler.checkReadWritePermissionsForExistingPath(target) ) {
                doc = XMLFileHandler.readXMLObjectFromFile(target.toString());
                workingDirectory = doc.getRootElement().getText();
            }
            if ( workingDirectory == "" ) {
                chooseWorkingDirectoryOrExit();
            }
            
        } catch (IOException | JDOMException e) {
            UiDialogs.appExitWithMessage("Exception:" + "\n" + e.getMessage() + "\n" + e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException 
     * 
     */
    private void chooseWorkingDirectoryOrExit() throws IOException {
        this.setWorkingDirectory(Chooser.directoryChooser("Arbeitsverzeichnis"));
        LOGGER.log(Level.FINE, "Arbeitsverzeichnis: {0}", this.workingDirectory);
        if ( workingDirectory == "" ) {
            UiDialogs.appExitWithMessage("Kein Arbeitsverzeichnis ausgewählt.");
        }
    }

    /**
     * @return the workingDirectory
     */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * @param workingDirectory the workingDirectory to set
     * @throws IOException 
     */
    public void setWorkingDirectory(String workingDirectory) throws IOException {
        workingDirectory.strip();
        LOGGER.log(Level.FINE, "Arbeitsverzeichnis (strip): {0}", workingDirectory);
        if ( FileHandler.checkDirectoryExistsAndPermissions( Path.of(workingDirectory) ) ) {
            LOGGER.log(Level.FINE, "SetWorkingDirectotry");
            this.workingDirectory = workingDirectory;
            doc.getRootElement().setText(workingDirectory);
            this.writeToXMLFile();
        }
    }
    
    private void writeToXMLFile() {
        try {
            XMLFileHandler.writeXMLObjectToFile(doc, workingDirectoryFilename);
        } catch (IOException e) {
            UiDialogs.appExitWithMessage("Exception:" + "\n" + e.getMessage() + "\n" + e.getCause());
            e.printStackTrace();
        }
    }
}
