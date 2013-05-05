package traypass.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import traypass.log.LogFactory;

public class ToolFTP {

	private static final Logger logger = LogFactory.getLogger(ToolFTP.class);

	private String host;

	private String user;

	private String password;

	private int port = 21;

	public ToolFTP(String host, int port, String user, String password) {
		super();
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = port;
	}

	public ToolFTP(String host, String user, String password) {
		super();
		this.host = host;
		this.user = user;
		this.password = password;
	}

	public synchronized boolean uploadFile(String serverFileName, String localFileName) {
		boolean result = true;
		URLConnection ftp;
		BufferedInputStream bis;
		BufferedOutputStream bos = null;
		try {
			ftp = connect(serverFileName);
			bis = new BufferedInputStream(new FileInputStream(localFileName));
			bos = new BufferedOutputStream(ftp.getOutputStream());
			byte[] buffer = new byte[1024];
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
		} catch (Exception e) {
			logger.error(e);
			result = false;
		} finally {
			try {
				bos.close();
			} catch (Exception e) {
				logger.error(e);
				result = false;
			}
			ftp = null;
		}
		return result;
	}

	public synchronized boolean downloadFile(String serverFileName, String localfilename) {
		boolean result = true;
		URLConnection ftp;
		InputStream is = null;
		BufferedInputStream bis;
		BufferedOutputStream bos = null;
		try {
			ftp = connect(serverFileName);
			is = ftp.getInputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(new FileOutputStream(localfilename));
			byte[] buffer = new byte[1024];
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}

		} catch (Exception e) {
			logger.error(e);
			result = false;
		} finally {
			try {
				bos.close();
			} catch (Exception e) {
				logger.error(e);
				result = false;
			}
			try {
				is.close();
			} catch (Exception e) {
				logger.error(e);
				result = false;
			}
			ftp = null;
		}
		return result;
	}

	private synchronized URLConnection connect(String targetFile) throws Exception {
		URL url = new URL("ftp://" + user + ":" + password + "@" + host + ":" + port + "/" + targetFile + ";type=i");
		URLConnection m_client = url.openConnection();
		return m_client;
	}
}