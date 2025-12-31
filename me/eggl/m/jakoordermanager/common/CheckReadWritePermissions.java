/**
 * 
 */
package me.eggl.m.jakoordermanager.common;

import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * 
 */
public class CheckReadWritePermissions {
    
    public static boolean forExistingFile(Path path) throws AccessDeniedException {
        if ( ! Files.isReadable(path) ) {
            throw new AccessDeniedException( String.format("Keine Leseberechtigung für die Datei: %s", path) );
        }
        if ( ! Files.isWritable(path) ) {
            throw new AccessDeniedException( String.format("Keine Schreibberechtigung für die Datei: %s", path) );
        }
        return true;
    }
}



















