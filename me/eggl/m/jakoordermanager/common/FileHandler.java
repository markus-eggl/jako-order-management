/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class provide only static methods for file and directory management.
 * 
 *  The methods are for checking the existence and read-/write permissions on
 *  directories and files. 
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
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
        LOGGER.setUseParentHandlers(false);
    }
    
    public static boolean checkReadWritePermissionsForExistingPath(Path path) throws IOException {
        if (path == null) {
            throw new NoSuchFileException("Der übergebene Pfad ist null");
        }
        if ( ! Files.isReadable(path) ) {
            return false;
            // throw new AccessDeniedException( String.format("Keine Leseberechtigung für die Datei: %s", path) );
        }
        if ( ! Files.isWritable(path) ) {
            return false;
            // throw new AccessDeniedException( String.format("Keine Schreibberechtigung für die Datei: %s", path) );
        }
        return true;
    }
    
    
    public static boolean checkIfFileExists(Path path) throws NoSuchFileException {
        return ( checkIfPathExists(path) && Files.isRegularFile(path) );
    }
    
    public static boolean checkIfDirectoryExists(Path path) throws NoSuchFileException {
        return ( checkIfPathExists(path) && Files.isDirectory(path) );
    }
    public static boolean checkIfDirectoryExists(String path) throws NoSuchFileException {
        if ( path == null || path.isBlank() ) {
            return false;
        }
        return checkIfDirectoryExists( Path.of(path) );
    }
    
    private static boolean checkIfPathExists(Path path) throws NoSuchFileException {
        if (path == null) {
            throw new NoSuchFileException("Der übergebene Pfad ist null");
        }
        LOGGER.log(Level.FINE, "{0} exist: {1}", new Object[] { GetSpecials.setTextColorForTerminal(path.toString(), "g"), Files.exists(path) } );
        return Files.exists(path);
    }
    
    public static boolean checkFileExistsAndPermissions(Path path) throws IOException {
        if ( Files.isDirectory(path) ) {
            throw new NoSuchFileException( path + " is a directory! \n It should be a file." );
        }
        return ( checkIfFileExists(path) & checkReadWritePermissionsForExistingPath(path) );
    }
    
    public static boolean checkDirectoryExistsAndPermissions(Path path) throws IOException {
        if ( Files.isRegularFile(path) ) {
            throw new NotDirectoryException( path + " is a file! \n It should be a directory." );
        }
        return ( checkIfDirectoryExists(path) & checkReadWritePermissionsForExistingPath(path)  );
    }
    public static boolean checkDirectoryExistsAndPermissions(String path) throws IOException {
        if ( path == null || path.isBlank() ) {
            return false;
        }
        return checkDirectoryExistsAndPermissions( Path.of(path) );
    }
    
    
    
}




















