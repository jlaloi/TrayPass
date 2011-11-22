package traypass;

import java.awt.SystemTray;

import javax.swing.UIManager;

import org.apache.log4j.Logger;

import traypass.log.LogFactory;

public class Launcher {
	
	private static final Logger logger = LogFactory.getLogger(Launcher.class);

	public static final String configFileNameParam = "configFileName:";

	public static void setParameters(String[] args) {
		for (String arg : args) {
			if (arg.startsWith(configFileNameParam)) {
				TrayPassObject.configFileName = arg.substring(arg.indexOf(':') + 1);
			} else {
				logger.warn("Unknown paremeter: " + arg);
			}
		}
	}

	public static void main(String[] args) {

		setParameters(args);
		logger.info(configFileNameParam + " " + TrayPassObject.configFileName);

		if (SystemTray.isSupported()) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
				logger.error(e);
			}
			TrayPassObject.trayConfig.load();
			TrayPassObject.compute();
			TrayPassObject.trayPass = new TrayPass();
		}

	}
}