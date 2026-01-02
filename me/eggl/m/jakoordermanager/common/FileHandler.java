/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class FileHandler {
    
    private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
    }
    
    public static boolean checkReadWritePermissionsForExistingPath(Path path) throws IOException {
        if (path == null) {
            throw new NoSuchFileException("Der übergebene Pfad ist null");
        }
        if ( ! Files.isReadable(path) ) {
            throw new AccessDeniedException( String.format("Keine Leseberechtigung für die Datei: %s", path) );
        }
        if ( ! Files.isWritable(path) ) {
            throw new AccessDeniedException( String.format("Keine Schreibberechtigung für die Datei: %s", path) );
        }
        return true;
    }
    
    
    public static boolean checkIfFileExists(Path path) throws NoSuchFileException {
        LOGGER.log(Level.FINE, "Path: {0}", path);
        LOGGER.log(Level.FINE, "Exist: {0}", checkIfPathExists(path));
        LOGGER.log(Level.FINE, "IsFile: {0}", Files.isRegularFile(path));
        return ( checkIfPathExists(path) && Files.isRegularFile(path) );
    }
    
    public static boolean checkIfDirectoryExists(Path path) throws NoSuchFileException {
        return ( checkIfPathExists(path) && Files.isDirectory(path) );
    }
    
    private static boolean checkIfPathExists(Path path) throws NoSuchFileException {
        if (path == null) {
            throw new NoSuchFileException("Der übergebene Pfad ist null");
        }
        return Files.exists(path);
    }
    
    public static boolean checkFileExistsAndPermissions(Path path) throws IOException {
        LOGGER.log(Level.FINE, "Exist: {0}", checkIfFileExists(path));
        LOGGER.log(Level.FINE, "Permission: {0}", checkReadWritePermissionsForExistingPath(path));
        return ( checkIfFileExists(path) & checkReadWritePermissionsForExistingPath(path) );
    }
    
    public static boolean checkDirectoryExistsAndPermissions(Path path) throws IOException {
        return ( checkIfDirectoryExists(path) & checkReadWritePermissionsForExistingPath(path)  );
    }
    
    
    
}




















