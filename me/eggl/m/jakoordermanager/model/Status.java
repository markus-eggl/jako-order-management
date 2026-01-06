/**
 * 
 */
package me.eggl.m.jakoordermanager.model;

/**
 * This enum represents the different status of orders.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public enum Status {
    
    ADDED,
    MISSING_PART,
    ORDERED_PART,
    AVAILABLE,
    FINISHED,
    
    ;
    /**
     * @return the name in lower case.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}













