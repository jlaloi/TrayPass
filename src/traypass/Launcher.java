package traypass;

import java.awt.SystemTray;
import java.awt.Toolkit;

import javax.swing.UIManager;

import traypass.syntax.Interpreter;

public class Launcher {

	public static void setParameters(String[] args) {
		for (String arg : args) {
			if (arg.startsWith("passFile:")) {
				TrayPassObject.passFile = arg.substring(arg.indexOf(':') + 1);
			} else if (arg.startsWith("imageFile:")) {
				TrayPassObject.iconFile = arg.substring(arg.indexOf(':') + 1);
				try {
					TrayPassObject.trayImageIcon = Toolkit.getDefaultToolkit().getImage(TrayPassObject.iconFile);
				} catch (Exception e) {
					Interpreter.showError("Load icon " + args[1] + ":\n" + e);
					e.printStackTrace();
				}
			} else if (arg.startsWith("configFileName:")) {
				TrayPassObject.configFileName = arg.substring(arg.indexOf(':') + 1);
			} else if (arg.startsWith("fileEncode:")) {
				TrayPassObject.fileEncode = arg.substring(arg.indexOf(':') + 1);
			} else if (arg.startsWith("consoleEncode:")) {
				TrayPassObject.consoleEncode = arg.substring(arg.indexOf(':') + 1);
			} else if (arg.startsWith("captureWidth:")) {
				TrayPassObject.captureWidth = Integer.valueOf(arg.substring(arg.indexOf(':') + 1));
			} else if (arg.startsWith("passFile:")) {
				TrayPassObject.passFile = arg.substring(arg.indexOf(':') + 1);
			} else if (arg.startsWith("fontName:")) {
				TrayPassObject.fontName = arg.substring(arg.indexOf(':') + 1);
			} else if (arg.startsWith("fontSize:")) {
				TrayPassObject.fontSize = Integer.valueOf(arg.substring(arg.indexOf(':') + 1));
			} else {
				System.out.println("Unknown paremeter : " + arg);
			}
		}
	}

	public static void main(String[] args) {

		setParameters(args);
		TrayPassObject.computeFont();

		System.out.println("Parameters:");
		System.out.println("passFile: " + TrayPassObject.passFile);
		System.out.println("imageFile: " + TrayPassObject.iconFile);
		System.out.println("configFileName: " + TrayPassObject.configFileName);
		System.out.println("fileEncode: " + TrayPassObject.fileEncode);
		System.out.println("consoleEncode: " + TrayPassObject.consoleEncode);
		System.out.println("captureWidth: " + TrayPassObject.captureWidth);
		System.out.println("fontName: " + TrayPassObject.fontName);
		System.out.println("fontSize: " + TrayPassObject.fontSize);
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