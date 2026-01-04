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
 * 
 */
public class MainFrame {

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
    public MainFrame(JOMWorkingDirectory jomConfiguration) {
        super();
        JFrame f = new JFrame("Order Manager");
        // addClosingQuery(f);
        addTabs(f, jomConfiguration);
        
        
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
    private void addTabs(JFrame f, JOMWorkingDirectory jomConfiguration) {
        JTabbedPane tabbedPane = new JTabbedPane();
        // tabbedPane.addTab("Neu", new JLabel("Tab Neu"));
        
        addConfigPanel(tabbedPane, jomConfiguration);
        
        f.add(tabbedPane);
    }

    /**
     * @param tabbedPane
     */
    private void addConfigPanel(JTabbedPane tabbedPane, JOMWorkingDirectory jomConfiguration) {
        JPanel configPanel = new JPanel();
        configPanel.add(new JLabel("Working directory:"));
        configPanel.add(createWorkingDirectoryTextField(jomConfiguration));
        tabbedPane.addTab("Configuration", configPanel);
    }

    /**
     * @param jomConfiguration
     * @return
     */
    private JTextField createWorkingDirectoryTextField(JOMWorkingDirectory jomConfiguration) {
        JTextField workingDirectory = new JTextField();
        workingDirectory.setText(jomConfiguration.getWorkingDirectory());
        workingDirectory.setEditable(false);
        workingDirectory.setMargin(GetSpecials.getInsetsForTextField());
        return workingDirectory;
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


















