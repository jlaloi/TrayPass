package traypass;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;

public class Launcher {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static final String configFileNameParam = "configFileName:";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			String configFile = Factory.defaultTrayConfigFile;
			if (args.length > 0 && args[0].startsWith(configFileNameParam)) {
				configFile = args[0].substring(args[0].indexOf(':') + 1);
			}
			new Factory(configFile);
		} catch (Exception e) {
			logger.error("Error", e);
		}
	}

}