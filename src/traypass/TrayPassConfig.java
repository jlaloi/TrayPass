package traypass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TrayPassConfig implements Serializable {

	private String cryptoExample = "";

	private String captureDir = "";

	private String proxyHost = "";

	private String proxyPass = "";

	private String proxyUser = "";

	private int proxyPort = 8080;

	private String font = "Calibri";

	private int fontSize = 11;

	private String fileEncode = "ISO-8859-1";

	private String consoleEncode = "CP850";

	private int captureWidth = 1024;

	private String passFile = "c:\\TrayPass.txt";

	private String iconFile = "DefaultIcon.png";

	private int imageCheckNumber = 60;

	private int imageCheckInterval = 400;

	private int iconSize = 16;

	public String getSaveFile() {
		String home = System.getenv("USERPROFILE");
		if (home == null || home.trim().length() == 0) {
			home = System.getenv("HOME");
		}
		return home + TrayPassObject.fileSeparator + TrayPassObject.configFileName;
	}

	public void save() {
		try {
			FileOutputStream fos = new FileOutputStream(getSaveFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			try {
				oos.writeObject(this);
				oos.flush();
			} finally {
				try {
					oos.close();
				} finally {
					fos.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try {
			FileInputStream fis = new FileInputStream(getSaveFile());
			ObjectInputStream ois = new ObjectInputStream(fis);
			TrayPassConfig tmp = null;
			try {
				tmp = (TrayPassConfig) ois.readObject();
			} finally {
				try {
					ois.close();
				} finally {
					fis.close();
				}
			}
			if (tmp != null) {
				this.setCryptoExample(tmp.getCryptoExample());
				this.setCaptureDir(tmp.getCaptureDir());
				this.setProxyHost(tmp.getProxyHost());
				this.setProxyPass(tmp.getProxyPass());
				this.setProxyUser(tmp.getProxyUser());
				this.setProxyPort(tmp.getProxyPort());
				this.setCaptureWidth(tmp.getCaptureWidth());
				this.setConsoleEncode(tmp.getConsoleEncode());
				this.setFileEncode(tmp.getFileEncode());
				this.setFont(tmp.getFont());
				this.setFontSize(tmp.getFontSize());
				this.setIconFile(tmp.getIconFile());
				this.setPassFile(tmp.getPassFile());
				this.setImageCheckInterval(tmp.getImageCheckInterval());
				this.setImageCheckNumber(tmp.getImageCheckNumber());
				this.setIconSize(tmp.getIconSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCryptoExample() {
		return cryptoExample;
	}

	public void setCryptoExample(String cryptoExample) {
		this.cryptoExample = cryptoExample;
	}

	public String getCaptureDir() {
		return captureDir;
	}

	public void setCaptureDir(String captureDir) {
		this.captureDir = captureDir;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPass() {
		return proxyPass;
	}

	public void setProxyPass(String proxyPass) {
		this.proxyPass = proxyPass;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getFileEncode() {
		return fileEncode;
	}

	public void setFileEncode(String fileEncode) {
		this.fileEncode = fileEncode;
	}

	public String getConsoleEncode() {
		return consoleEncode;
	}

	public void setConsoleEncode(String consoleEncode) {
		this.consoleEncode = consoleEncode;
	}

	public int getCaptureWidth() {
		return captureWidth;
	}

	public void setCaptureWidth(int captureWidth) {
		this.captureWidth = captureWidth;
	}

	public String getPassFile() {
		return passFile;
	}

	public void setPassFile(String passFile) {
		this.passFile = passFile;
	}

	public String getIconFile() {
		return iconFile;
	}

	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}

	public int getImageCheckInterval() {
		return imageCheckInterval;
	}

	public void setImageCheckInterval(int imageCheckInterval) {
		this.imageCheckInterval = imageCheckInterval;
	}

	public int getImageCheckNumber() {
		return imageCheckNumber;
	}

	public void setImageCheckNumber(int imageCheckNumber) {
		this.imageCheckNumber = imageCheckNumber;
	}

	public int getIconSize() {
		return iconSize;
	}

	public void setIconSize(int iconSize) {
		this.iconSize = iconSize;
	}

}