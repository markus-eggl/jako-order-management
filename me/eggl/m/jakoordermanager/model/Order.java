/**
 * 
 */
package me.eggl.m.jakoordermanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import me.eggl.m.jakoordermanager.common.*;

/**
 * 
 */
public class Order {
    
    private LocalDate acceptedOn;
    private Client client;
    private Status status;
    private BigDecimal costEstinateGross;
    private BigDecimal costActualGross;
    private BigDecimal costMaxGross;
    private Part[] parts;

}


















