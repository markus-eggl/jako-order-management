/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import me.eggl.m.jakoordermanager.ui.Chooser;
import me.eggl.m.jakoordermanager.ui.UiDialogs;

/**
 * 
 */
public class JOMConfiguration implements CopyXMLTemplateToDirectory {
    private final String workingDirectoryFilename = "JOMWorkingDirectory.xml";
    private String workingDirectory;
    private Document doc;
    
    /**
     * 
     */
    public JOMConfiguration() {
        super();
        try {
            Path source = Path.of(templatesDirectory.toString(), workingDirectoryFilename);
            Path target = Path.of(".", workingDirectoryFilename);
            
            if ( ! ( Files.exists(target) && Files.isRegularFile(target) ) ) {
                copyTemplateInDirectory(source, target);
            }
            if ( CheckReadWritePermissions.forExistingFile(target) ) {
                doc = new SAXBuilder().build(target.toString());
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
     * 
     */
    private void chooseWorkingDirectoryOrExit() {
        this.setWorkingDirectory(Chooser.directoryChooser("Arbeitsverzeichnis"));
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
     */
    public void setWorkingDirectory(String workingDirectory) {
        if ( workingDirectory != "" ) {
            this.workingDirectory = workingDirectory;
            doc.getRootElement().setText(workingDirectory);
            this.writeToXMLFile();
        }
    }
    
    private void writeToXMLFile() {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try (FileWriter writer = new FileWriter(workingDirectoryFilename)) {
            xmlOutput.output(doc, writer);
        } catch (IOException e) {
            UiDialogs.appExitWithMessage("Exception:" + "\n" + e.getMessage() + "\n" + e.getCause());
            e.printStackTrace();
        }
    }
}
