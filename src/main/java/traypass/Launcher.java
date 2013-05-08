package traypass;

import java.awt.SystemTray;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;

public class Launcher {

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static final String configFileNameParam = "configFileName:";

	public static void setParameters(String[] args) {
		for (String arg : args) {
			if (arg.startsWith(configFileNameParam)) {
				Factory.configFileName = arg.substring(arg.indexOf(':') + 1);
			} else {
				logger.warn("Unknown paremeter: " + arg);
			}
		}
	}

	public static void main(String[] args) {

		setParameters(args);
		logger.info(configFileNameParam + " " + Factory.configFileName);

		if (SystemTray.isSupported()) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
				logger.error("Error", e);
			}
			Factory.trayConfig.load();
			Factory.compute();
			Factory.trayPass = new TrayPass();
		}

	}
}