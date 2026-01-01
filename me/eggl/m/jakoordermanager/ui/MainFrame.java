/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import java.awt.event.*;
import javax.swing.*;

import me.eggl.m.jakoordermanager.common.JOMConfiguration;

/**
 * 
 */
public class MainFrame {

    /**
     * 
     */
    public MainFrame(JOMConfiguration jomConfiguration) {
        super();
        JFrame f = new JFrame("Order Manager");
        // addClosingQuery(f);
        addTabs(f);
        
        
        setShowOptions(f);
    }

    /**
     * @param f
     */
    private void setShowOptions(JFrame f) {
        f.setSize(500, 300);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * @param f
     */
    private void addTabs(JFrame f) {
        JTabbedPane tabbedPane = new JTabbedPane();
        // tabbedPane.addTab("Neu", new JLabel("Tab Neu"));
        
        addConfigPanel(tabbedPane);
        
        f.add(tabbedPane);
    }

    /**
     * @param tabbedPane
     */
    private void addConfigPanel(JTabbedPane tabbedPane) {
        JPanel configPanel = new JPanel();
        configPanel.add(new JLabel("Arbeitsverzeichnis:"));
        configPanel.add(new JTextField(20));
        tabbedPane.addTab("Configuration", configPanel);
    }

    /**
     * @param f
     */
    private void addClosingQuery(JFrame f) {
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener( new WindowAdapter() {
            @Override public void windowClosing( WindowEvent event ) {
                int option = JOptionPane.showConfirmDialog(null, "Programm beenden?" );
                if ( option == JOptionPane.OK_OPTION ) {
                    System.exit(0);
                }
            }
        } );
    }
    
}


















