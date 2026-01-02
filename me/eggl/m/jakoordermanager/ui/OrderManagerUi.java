/**
 * 
 */
package me.eggl.m.jakoordermanager.ui;

import me.eggl.m.jakoordermanager.core.JOMConfiguration;
/**
 * 
 */
public class OrderManagerUi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JOMConfiguration jomConfiguration = new JOMConfiguration();
		new MainFrame(jomConfiguration);
	}

}
