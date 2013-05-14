package traypass;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;

public class Launcher {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			String configFile = Factory.defaultTrayConfigFile;
			if (args.length > 0) {
				configFile = args[0];
			}
			new Factory(configFile);
		} catch (Exception e) {
			logger.error("Error", e);
		}
	}

}