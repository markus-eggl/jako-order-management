/**
 * 
 */
package me.eggl.m.jakoordermanager.model;

import java.time.LocalDate;

import me.eggl.m.jakoordermanager.common.Status;

/**
 * 
 */
public class Part {
    
    private String name;
    // TODO check type for euro
    // TODO check 'price', brutto / netto, rename
    private double price;
    private LocalDate deliveryTime;
    private Status status;
}
