/**
 * 
 */
package me.eggl.m.jakoordermanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 */
public class Part {
    
    private String name;
    // TODO set rundungsmodus / check for only 2 decimals
    private BigDecimal priceGross;
    private LocalDate deliveryTime;
    private Status status;
    
}
