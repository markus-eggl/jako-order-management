/**
 * 
 */
package me.eggl.m.jakoordermanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import me.eggl.m.jakoordermanager.common.Status;

/**
 * 
 */
public class Part {
    
    private String name;
    private BigDecimal priceGross;
    // TODO check type for Date
    private LocalDate deliveryTime;
    private Status status;
    
    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return priceGross;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        // TODO set rundungsmodus / check for only 2 decimals
        this.priceGross = price;
    }
    
}
