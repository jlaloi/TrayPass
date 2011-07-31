package traypass.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import traypass.TrayPassConfig;
import traypass.TrayPassObject;
import traypass.crypto.CryptoEncrypter;
import traypass.syntax.Interpreter;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ToolFile {

	public static List<String> getFileLines(String file) {
		List<String> result = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, TrayPassObject.fileEncode));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			in.close();
		} catch (Exception e) {
			Interpreter.showError("getFileLines " + file + ":\n" + e);
			e.printStackTrace();
		}
		return result;
	}

	public static String getFileContent(String file) {
		String result = "";
		for (String line : getFileLines(file)) {
			result += line + TrayPassObject.lineSeparator;
		}
		return result;
	}

	public static void addToFile(String file, String str) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(str);
			bw.newLine();
			bw.flush();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception ioe2) {
				ioe2.printStackTrace();
			}
		}
	}

	public static URLConnection getURConnection(String host) {
		URLConnection connection = null;
		try {
			URL url = new URL(host);
			connection = url.openConnection();
			TrayPassConfig config = TrayPassObject.trayConfig;
			if (config != null && config.getProxyHost() != null && config.getProxyHost().trim().length() > 0 && config.getProxyPort() > 0) {
				System.setProperty("http.proxyHost", config.getProxyHost());
				System.setProperty("http.proxyPort", config.getProxyPort() + "");
			}
			if (config != null && config.getProxyUser() != null && config.getProxyUser().trim().length() > 0 && config.getProxyPass() != null && config.getProxyPass().trim().length() > 0 && TrayPassObject.secretKey != null) {
				String password = CryptoEncrypter.decrypt(config.getProxyPass(), TrayPassObject.secretKey);
				String encoded = new String(Base64.encode(new String(config.getProxyUser() + ":" + password).getBytes()));
				connection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static int getDownloadSize(String url) {
		int result = 0;
		try {
			URLConnection connection = getURConnection(url);
			result = connection.getContentLength();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void downloadFile(String host, String file) {
		InputStream input = null;
		FileOutputStream writeFile = null;
		try {
			URLConnection connection = getURConnection(host);
			input = connection.getInputStream();
			writeFile = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int read;
			while ((read = input.read(buffer)) > 0) {
				writeFile.write(buffer, 0, read);
			}
			writeFile.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writeFile != null) {
					writeFile.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String formatSize(double size) {
		double d1024 = new Double(1024);
		NumberFormat myformat = NumberFormat.getInstance();
		myformat.setMaximumFractionDigits(2);
		myformat.setMinimumFractionDigits(2);
		if (size < d1024) {
			return myformat.format(size) + " Byte";
		}
		size /= d1024;
		if (size < 1024) {
			return myformat.format(size) + " Ko";
		}
		size /= d1024;
		if (size < 1024) {
			return myformat.format(size) + " Mo";
		}
		size /= d1024;
		if (size < 1024) {
			return myformat.format(size) + " Go";
		}
		size /= d1024;
		return myformat.format(size) + " To";
	}

	public static boolean copyFile(String currentFile, String newFile) {
		boolean result = true;
		int bufferSize = 2048;
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			in = new BufferedReader(new FileReader(currentFile), bufferSize);
			out = new BufferedWriter(new FileWriter(newFile), bufferSize);
			int s = in.read();
			while (s != -1) {
				out.write(s);
				s = in.read();
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public static String getTmpDir() {
		String result;
		if (System.getenv("TMP") != null && System.getenv("TMP").trim().length() > 0) {
			result = System.getenv("TMP") + TrayPassObject.fileSeparator + TrayPassObject.tmpDir + TrayPassObject.fileSeparator;
		} else {
			result = "/tmp" + TrayPassObject.fileSeparator + TrayPassObject.tmpDir + TrayPassObject.fileSeparator;
		}
		return result;
	}
}