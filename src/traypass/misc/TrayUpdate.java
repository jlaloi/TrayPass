package traypass.misc;

import java.util.jar.JarFile;

import javax.swing.JOptionPane;

import traypass.TrayPassObject;
import traypass.tools.ToolDownload;
import traypass.tools.ToolFile;

public class TrayUpdate {

	public static String updateJarUrl = "http://tp.loul.org/TrayPass.jar";

	public static String updateVersionUrl = "http://tp.loul.org/version.txt";

	public static String manifestAttribute = "Implementation-Version";

	public String getJarLocation() {
		String result = "";
		try {
			result = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString().replace("file:/", "").replace("/", TrayPassObject.fileSeparator + "").replace("%20", " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getLocalVersion() {
		return getLocalVersion(getJarLocation());
	}

	public String getLocalVersion(String jarLocation) {
		String result = "";
		try {
			JarFile jar = new JarFile(jarLocation);
			result = jar.getManifest().getMainAttributes().getValue(manifestAttribute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getServerVersion() {
		String result = "";
		try {
			String tmpFile = ToolFile.getTmpDir() + "tpversion";
			ToolDownload.downloadFile(updateVersionUrl, tmpFile);
			result = ToolFile.getFileLines(tmpFile).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isUpdate() {
		boolean result = false;
		try {
			String local = getLocalVersion(getJarLocation());
			String server = getServerVersion();
			System.out.println("Local version is " + local);
			System.out.println("Server version is " + server);
			if (local != null && server != null && local.trim().length() > 0 && server.trim().length() > 0 && !local.equals(server)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update() {
		ToolDownload.downloadFile(updateJarUrl, getJarLocation());
	}

	public void manage() {
		if (isUpdate()) {
			Object[] options = { "Yes, update it!", "No, thanks" };
			int n = JOptionPane.showOptionDialog(null, "A new update is available.\n" + "Local : " + getLocalVersion() + "\n" + "Server : " + getServerVersion() + "\n" + "Do you wants to update?", "TrayPass update", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == 0) {
				update();
				TrayPassObject.trayPass.showInfo(getJarLocation() + " updated.\nYou need to restart the application!");
			}
		} else {
			TrayPassObject.trayPass.showInfo("No update available!");
		}
	}

}