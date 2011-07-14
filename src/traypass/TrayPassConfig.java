package traypass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TrayPassConfig implements Serializable {

	private String cryptoExample = "";

	private String captureDir = "";

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
}