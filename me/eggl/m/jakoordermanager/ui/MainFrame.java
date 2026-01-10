/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import me.eggl.m.jakoordermanager.core.JOMSubDirectories;
import me.eggl.m.jakoordermanager.core.JOMWorkingDirectory;
import me.eggl.m.jakoordermanager.model.Status;
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
    
    private static final Logger LOGGER = Logger.getLogger(MainFrame.class.getName());
    static {
        // Level: OFF, INFO, FINE
        final Level LOGLEVEL = Level.INFO;
        LOGGER.setLevel(LOGLEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGLEVEL);
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false);
    }
    
    private JTextField workingDirectory;
    private Map<Status, JTextField> subDirectoryMap = new HashMap<>();
    private JOMWorkingDirectory jomConfiguration;
    private JOMSubDirectories subDirectories;
    
    /**
     * Create the main window of the app.
     * 
     * The method add different Swing elements and show it to the user.
     * 
     * @param jomConfiguration information about directories,...
     */
    public MainFrame(JOMWorkingDirectory jomConfiguration, JOMSubDirectories subDirectories) {
        super();
        JFrame f = new JFrame("Order Manager");
        // addClosingQuery(f);
        this.jomConfiguration = jomConfiguration;
        this.subDirectories = subDirectories;
        
        addTabs(f);
        
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
    private void addTabs(JFrame f) {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        addNewPanel(tabbedPane);
        
        addConfigPanel(tabbedPane);
        f.add(tabbedPane);
    }
    
    
    private void addNewPanel(JTabbedPane tabbedPane) {
        JPanel newPanel = new JPanel();
        
        
        
        tabbedPane.addTab("Neu", newPanel);
    }
    

    /**
     * The method add the config tab.
     * 
     * @param tabbedPane container element for the tabs.
     */
    private void addConfigPanel(JTabbedPane tabbedPane) {
        JPanel configPanel = new JPanel();
        configPanel.add(createConfigPanelWorkingDirectory());
        configPanel.add(createConfigPanelSubDirectories());
        tabbedPane.addTab("Configuration", configPanel);
    }

    /**
     * @param subDirectories
     * @return
     */
    private JPanel createConfigPanelSubDirectories() {
        JPanel configPaneSubDirectories = new JPanel(new GridLayout(0, 2, 10, 5));
        subDirectories.getSubDirectories().forEach( (status, directory) -> {
            configPaneSubDirectories.add( new JLabel( status.toString() + ":" ) );
            configPaneSubDirectories.add( createSubDirectoryTextField(status, directory) );
        });
        return configPaneSubDirectories;
    }

    /**
     * @param directory
     * @return
     */
    private JTextField createSubDirectoryTextField(Status status, Path directory) {
        JTextField textField = new JTextField(directory.toString(), 20);
        subDirectoryMap.put(status, textField);
        textField.setMargin(GetSpecials.getInsetsForTextField());
        textField.setEditable(false);
        return textField;
    }

    /**
     * @param jomConfiguration
     * @return
     */
    private JPanel createConfigPanelWorkingDirectory() {
        JPanel configPanelWorkingDirectory = new JPanel();
        configPanelWorkingDirectory.add(new JLabel("Working directory:"));
        configPanelWorkingDirectory.add(createWorkingDirectoryTextField(jomConfiguration));
        configPanelWorkingDirectory.add(createWorkingDirectoryChangeButton());
        return configPanelWorkingDirectory;
    }

    
    private JButton createWorkingDirectoryChangeButton() {
        final JButton change = new JButton("Chance Directory");
        change.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                jomConfiguration.changeWorkingDirectory();
                workingDirectory.setText(jomConfiguration.getWorkingDirectory());
                subDirectories.setAndCreateSubDirectories(jomConfiguration.getWorkingDirectory());
                LOGGER.log(Level.INFO, "SubDirectoryMap: {0}", subDirectoryMap);
                subDirectoryMap.forEach( (status, textField) -> {
                    textField.setText( subDirectories.getSubDirectoryFor(status).toString() );
                });
            }
        });
        return change;
    }
    

    /**
     * The method create the Elements for showing and changing
     * the working directory.
     * 
     * @param jomConfiguration informations about directories.
     * @return the textfield for the working directory.
     */
    private JTextField createWorkingDirectoryTextField(JOMWorkingDirectory jomConfiguration) {
        this.workingDirectory = new JTextField(19);
        this.workingDirectory.setText(jomConfiguration.getWorkingDirectory());
        this.workingDirectory.setEditable(false);
        this.workingDirectory.setMargin(GetSpecials.getInsetsForTextField());
        return this.workingDirectory;
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


















