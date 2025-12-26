/**
 * 
 */
package me.eggl.m.jakoordermanager.model;

import me.eggl.m.jakoordermanager.common.*;

/**
 * 
 */
public class Order {
    
    private Client client;
    private Status status;
    // TODO check type for euros
    private double costEstinate;
    private double costActual;
    private double costMax;
    private Part[] parts;

}
