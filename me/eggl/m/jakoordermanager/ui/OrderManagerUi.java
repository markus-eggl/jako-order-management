/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import me.eggl.m.jakoordermanager.core.JOMWorkingDirectory;

/**
 * The main class for the app.
 * 
 * @author Markus Eggl
 * @version 1.0 (2026)
 * @since 1.0
 */
public class OrderManagerUi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JOMWorkingDirectory jomConfiguration = JOMWorkingDirectory.getInstance();
		new MainFrame(jomConfiguration);
	}

}
