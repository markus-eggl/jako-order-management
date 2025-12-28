/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import java.awt.Color;

import javax.swing.*;

/**
 * 
 */
public class MainFrame {

    /**
     * 
     */
    public MainFrame() {
        super();
        JFrame f = new JFrame("Order Manager");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250, 100);
        
        JLabel l = new JLabel("<html>Huhu.<p>Jetzt bin <strong>ich</strong> hier.</p></html>");
        l.setForeground(Color.BLUE);
        f.add( l );
        
        f.pack();
        f.setVisible(true);
    }
    
}


















