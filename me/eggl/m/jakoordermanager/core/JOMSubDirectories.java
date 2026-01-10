/**
 * 
 */
package me.eggl.m.jakoordermanager.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.eggl.m.jakoordermanager.model.Status;
import me.eggl.m.jakoordermanager.common.FileHandler;
import me.eggl.m.jakoordermanager.common.GetSpecials;

/**
 * This class manage the sub directories for the app.
 * 
 * For every status it would be a sub directory available. The sub
 * directories are in the working directory. It is not possible to
 * chance the names of the sub directories.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class JOMSubDirectories {

    private static final Logger LOGGER = Logger.getLogger(JOMSubDirectories.class.getName());
    private static JOMSubDirectories instance = new JOMSubDirectories();
    
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false);
    }
    
    private final EnumMap<Status, Path> subDirectories = new EnumMap<>(Status.class);
    
    
    /**
     * Private constructor to prevent instantiation from outside.
     */
    private JOMSubDirectories() {
        super();
    }


    /**
     * @return the instance
     */
    public static JOMSubDirectories getInstance() {
        return instance;
    }


    /**
     * @return the subDirectories
     */
    public EnumMap<Status, Path> getSubDirectories() {
        return this.subDirectories;
    }
    
    /**
     * @return All sub directories in a List<String>
     */
    public List<String> getAllSubDirectories() {
        LOGGER.log(Level.FINE, "\n\tDirectories: {0}", this.getSubDirectories().toString());
        return this.getSubDirectories().keySet().stream().map(Status::toString).toList();
    }

    /**
     * @param status
     * @return the path of the sub Directory of the status.
     */
    public Path getSubDirectoryFor(Status status) {
        return this.getSubDirectories().get(status);
    }
    
    /**
     * This method update the sub directories.
     * 
     * If the working directory has changed, the sub directories
     * have to be update. Also have the sub directories to be
     * created again if they not exist yet. 
     * 
     * @param workingDirectory
     */
    public void setAndCreateSubDirectories(String workingDirectory) {
        try {
            this.setSubDirectories(workingDirectory);
            this.createSubDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method updates the internal EnumMap.
     * 
     * @param workingDirectory
     * @throws IOException
     */
    private void setSubDirectories(String workingDirectory) throws IOException {
        if ( ! FileHandler.checkDirectoryExistsAndPermissions(workingDirectory) ) {
            return;
        }
        for ( Status status : Status.values() ) {
            Path path = Path.of(workingDirectory, status.toString());
            this.getSubDirectories().put(status, path);
            LOGGER.log(Level.FINE, "SubDirectories: {0}", 
                    GetSpecials.setTextColorForTerminal( this.getSubDirectories().get(status).toString(), "g") );
        }
        LOGGER.log(Level.INFO, "SubDirectories: {0}", this.getSubDirectories().toString());
    }
    
    /**
     * This method creates the sub directories if they not exist yet.
     * 
     * The read- and writepermissions will be checked before.
     * 
     * @throws IOException if problems about the filesystem rises.
     */
    private void createSubDirectories() throws IOException {
        for ( Status status : this.getSubDirectories().keySet() ) {
            if ( status == null ) {
                continue;
            }
            Path subDirectory = this.getSubDirectoryFor(status);
            Files.createDirectories(subDirectory);
            LOGGER.log(Level.INFO, "SubDirectory: {0} erstellt.", 
                    GetSpecials.setTextColorForTerminal( subDirectory.toString(), "g") );
        }
    }
}

















