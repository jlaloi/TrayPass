package traypass.misc;

import java.util.jar.JarFile;

import traypass.TrayPassObject;
import traypass.tools.ToolFile;

public class TrayUpdate {

	public static String updateJarUrl = "http://loul.org/traypass/TrayPass.jar";

	public static String updateVersionUrl = "http://loul.org/traypass/version.txt";

	public static String manifestAttribute = "Implementation-Version";

	public String getJarLocation() {
		String result = "";
		try {
			result = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getLocalVersion(){
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
			String tmpFile = ToolFile.getTmpDir() + "trayPass.version";
			ToolFile.downloadFile(updateVersionUrl, tmpFile);
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
		if (isUpdate()) {
			ToolFile.downloadFile(updateJarUrl, getJarLocation());
			TrayPassObject.trayPass.showInfo(getJarLocation() + " updated.\nYou need to restart the application!");
		} else {
			TrayPassObject.trayPass.showInfo("No update available!");
		}
	}

}