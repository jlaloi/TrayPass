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

}