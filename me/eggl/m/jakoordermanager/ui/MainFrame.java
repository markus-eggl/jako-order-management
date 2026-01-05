/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import me.eggl.m.jakoordermanager.core.JOMWorkingDirectory;
import me.eggl.m.jakoordermanager.common.GetSpecials;

/**
 * Class for the main UI window in the app.
 * 
 * The UI is created in Swing. The UI would be created on currend data in the
 * working directory. 
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class MainFrame {
    
    /**
     * Create the main window of the app.
     * 
     * The method add different Swing elements and show it to the user.
     * 
     * @param jomConfiguration information about directories,...
     */
    public MainFrame(JOMWorkingDirectory jomConfiguration) {
        super();
        JFrame f = new JFrame("Order Manager");
        // addClosingQuery(f);
        addTabs(f, jomConfiguration);
        
        
        setShowOptions(f);
    }

    /**
     * Set parameter for the window.
     * 
     * @param f the main frame.
     */
    private void setShowOptions(JFrame f) {
        f.setSize(500, 300);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * The method add the different tabs to the main window.
     * 
     * @param f main frame.
     */
    private void addTabs(JFrame f, JOMWorkingDirectory jomConfiguration) {
        JTabbedPane tabbedPane = new JTabbedPane();
        // tabbedPane.addTab("Neu", new JLabel("Tab Neu"));
        
        addConfigPanel(tabbedPane, jomConfiguration);
        
        f.add(tabbedPane);
    }

    /**
     * The method add the confic tab.
     * 
     * @param tabbedPane container element for the tabs.
     */
    private void addConfigPanel(JTabbedPane tabbedPane, JOMWorkingDirectory jomConfiguration) {
        JPanel configPanel = new JPanel();
        configPanel.add(new JLabel("Working directory:"));
        configPanel.add(createWorkingDirectoryTextField(jomConfiguration));
        tabbedPane.addTab("Configuration", configPanel);
    }

    /**
     * The method create the Elements for showing and changing
     * the working directory.
     * 
     * @param jomConfiguration informations about directories.
     * @return the textfield for the working directory.
     */
    private JTextField createWorkingDirectoryTextField(JOMWorkingDirectory jomConfiguration) {
        JTextField workingDirectory = new JTextField();
        workingDirectory.setText(jomConfiguration.getWorkingDirectory());
        workingDirectory.setEditable(false);
        workingDirectory.setMargin(GetSpecials.getInsetsForTextField());
        return workingDirectory;
    }

    /**
     * The method adds a additional dialog if the user will close
     * the app.
     * 
     * This prevent a closing by accident. The user has to be confirm
     * the closing.
     * 
     * @param f the main frame.
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


















