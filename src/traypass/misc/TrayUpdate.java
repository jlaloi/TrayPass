package traypass.misc;

import java.io.File;
import java.net.URISyntaxException;

import traypass.TrayPassObject;
import traypass.tools.ToolFile;

public class TrayUpdate {
	
	public static String updateUrl = "http://loul.org/traypass/TrayPass.jar";
	
	public String getJar(){
		String result = "";
		try {
			result = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isUpdate(){
		boolean result = false;
		try{
			long jarSize = new File(getJar()).length();
			long hostSize = ToolFile.getDownloadSize(updateUrl);
			result = hostSize != jarSize && jarSize > 0 && hostSize > 0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void update(){
		if(isUpdate()){
			ToolFile.downloadFile(updateUrl, getJar());
			TrayPassObject.trayPass.showInfo(getJar()+" updated.\nYou need to restart the application!");
		}else{
			TrayPassObject.trayPass.showInfo("No update available!");
		}
	}
	
}
