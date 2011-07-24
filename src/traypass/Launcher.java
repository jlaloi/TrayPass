package traypass;

import java.awt.SystemTray;

import javax.swing.UIManager;

public class Launcher {

	public static void setParameters(String[] args) {
		for (String arg : args) {
			if (arg.startsWith("configFileName:")) {
				TrayPassObject.configFileName = arg.substring(arg.indexOf(':') + 1);
			} else {
				System.out.println("Unknown paremeter : " + arg);
			}
		}
	}

	public static void main(String[] args) {

		setParameters(args);
		System.out.println("configFileName: " + TrayPassObject.configFileName);

		if (SystemTray.isSupported()) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
			TrayPassObject.trayConfig.load();
			TrayPassObject.compute();
			TrayPassObject.trayPass = new TrayPass();
		}

	}
}