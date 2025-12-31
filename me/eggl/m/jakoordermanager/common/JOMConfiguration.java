/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * 
 */
public class JOMConfiguration implements CopyXMLTemplateToDirectory {
    private final String workingDirectoryFilename = "JOMWorkingDirectory.xml";
    private String workingDirectory;
    
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
                workingDirectory = "/pfad/zum/Ordner";
            }
        } catch (IOException e) {
            // TODO Dialog
            e.printStackTrace();
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
        this.workingDirectory = workingDirectory;
    }

}
