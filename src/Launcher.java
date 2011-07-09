import java.awt.SystemTray;
import java.awt.Toolkit;

import javax.swing.UIManager;

public class Launcher {

	public static void main(String[] args) {
		if (args.length > 0) {
			TrayObject.passFile = args[0];
		}

		if (args.length > 1) {
			TrayObject.iconFile = args[1];
			try {
				TrayObject.trayImageIcon = Toolkit.getDefaultToolkit().getImage(TrayObject.iconFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (args.length > 2) {
			TrayObject.configFileName = args[2];
		}

		System.out.println("passFile: " + TrayObject.passFile);
		System.out.println("imageFile: " + TrayObject.iconFile);
		System.out.println("configFileName: " + TrayObject.configFileName);

		if (SystemTray.isSupported()) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
			TrayObject.trayConfig.load();
			new TrayPass();
		}
	}
}
