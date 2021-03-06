package traypass.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.configuration.TrayPassConfig;
import traypass.crypto.CryptoEncrypter;
import traypass.ressources.Factory;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ToolDownload {

	private static final Logger logger = LoggerFactory.getLogger(ToolDownload.class);

	public static HttpURLConnection getURConnection(String url) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			TrayPassConfig config = Factory.get().getConfig();
			if (config != null && config.getProxyHost() != null && config.getProxyHost().trim().length() > 0 && config.getProxyPort() > 0) {
				System.setProperty("http.proxyHost", config.getProxyHost());
				System.setProperty("http.proxyPort", config.getProxyPort() + "");
			}
			if (config != null && config.getProxyUser() != null && config.getProxyUser().trim().length() > 0 && config.getProxyPass() != null && config.getProxyPass().trim().length() > 0 && Factory.get().getSecretKey() != null) {
				String password = CryptoEncrypter.decrypt(config.getProxyPass(), Factory.get().getSecretKey());
				String encoded = new String(Base64.encode(new String(config.getProxyUser() + ":" + password).getBytes()));
				connection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
			}
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return connection;
	}

	public static int getDownloadSize(String url) {
		int result = 0;
		try {
			URLConnection connection = getURConnection(url);
			result = connection.getContentLength();
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return result;
	}

	public static String downloadFile(String url, String file) {
		String result = null;
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			randomAccessFile.setLength(getDownloadSize(url));
			downloadFile(url, randomAccessFile, 0, 0);
			randomAccessFile.close();
			result = file;
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return result;
	}

	public static void downloadFile(String url, RandomAccessFile file, long from, long to) {
		InputStream inputStream = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			if (to > 0) {
				connection.setRequestProperty("Range", "bytes=" + from + "-" + to);
			}
			connection.setRequestMethod("GET");
			connection.setReadTimeout(15 * 1000);
			connection.connect();
			byte buf[] = new byte[1024];
			int len;
			int pos = (int) from;
			inputStream = connection.getInputStream();
			while ((len = inputStream.read(buf)) > 0) {
				synchronized (file) {
					file.seek(pos);
					file.write(buf, 0, len);
				}
				pos += len;
			}
		} catch (Exception e) {
			logger.error("Error", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					logger.error("Error", ioe);
				}
			}
		}
	}

	public static List<String> getDownloadedFileContent(String url) {
		List<String> result = null;
		String tempFile = ToolFile.getNewTempFile();
		if (downloadFile(url, tempFile) != null) {
			result = ToolFile.getFileLines(tempFile);
		}
		new File(tempFile).delete();
		return result;
	}

}
