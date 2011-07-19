package traypass;

import java.awt.SystemTray;
import java.awt.Toolkit;

import javax.swing.UIManager;

import traypass.syntax.Interpreter;

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
				Interpreter.showError("Load icon " + args[1] + ":\n" + e);
				e.printStackTrace();
			}
		}

		if (args.length > 2) {
			TrayPassObject.configFileName = args[2];
		}

		if (args.length > 3) {
			TrayPassObject.fileEncode = args[3];
		}

		if (args.length > 4) {
			TrayPassObject.consoleEncode = args[4];
		}

		if (args.length > 5) {
			TrayPassObject.captureWidth = Integer.valueOf(args[5]);
		}

		System.out.println("Parameters:");
		System.out.println("passFile: " + TrayPassObject.passFile);
		System.out.println("imageFile: " + TrayPassObject.iconFile);
		System.out.println("configFileName: " + TrayPassObject.configFileName);
		System.out.println("fileEncode: " + TrayPassObject.fileEncode);
		System.out.println("consoleEncode: " + TrayPassObject.consoleEncode);
		System.out.println("captureWidth: " + TrayPassObject.captureWidth);
		System.out.println("");

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