package traypass;

import java.awt.SystemTray;
import java.awt.Toolkit;

import javax.swing.UIManager;

public class Launcher {

	public static void main(String[] args) {
		if (args.length > 0) {
			TrayPassObject.passFile = args[0];
		}

		if (args.length > 1) {
			TrayPassObject.iconFile = args[1];
			try {
				TrayPassObject.trayImageIcon = Toolkit.getDefaultToolkit().getImage(TrayPassObject.iconFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (args.length > 2) {
			TrayPassObject.configFileName = args[2];
		}

		System.out.println("passFile: " + TrayPassObject.passFile);
		System.out.println("imageFile: " + TrayPassObject.iconFile);
		System.out.println("configFileName: " + TrayPassObject.configFileName);

		if (SystemTray.isSupported()) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
			TrayPassObject.trayConfig.load();
			new TrayPass();
		}
	}
}
