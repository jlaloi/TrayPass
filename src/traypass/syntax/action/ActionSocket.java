package traypass.syntax.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.apache.log4j.Logger;

import traypass.TrayPassObject;
import traypass.log.LogFactory;
import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionSocket extends Action {

	private static final Logger logger = LogFactory.getLogger(ActionSocket.class);

	public static int bufferSize = 1024;

	public static String server = "server";

	public static String client = "client";

	public String doAction(List<String> parameters) {
		String result = "";
		String action = parameters.get(0);
		int port = Integer.valueOf(parameters.get(1));
		String path = parameters.get(2);
		byte[] buffer = new byte[bufferSize];
		int in;
		try {
			if (client.equals(action)) {
				String host = parameters.get(3);
				Socket sock = new Socket(host, port);
				InputStream is = sock.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				bis.read(buffer);
				String fileName = (new String(buffer)).replaceAll("\u0000.*", "");
				bis.read(buffer);
				long fileSize = Long.valueOf((new String(buffer)).replaceAll("\u0000.*", "")).longValue();
				TrayPassObject.trayPass.showInfo("Downloading " + fileName + " (" + ToolFile.formatSize(fileSize) + ")");
				FileOutputStream fos = new FileOutputStream(path + TrayPassObject.fileSeparator + fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				while ((in = bis.read(buffer)) != -1 && !interpreter.isStop()) {
					bos.write(buffer, 0, in);
				}
				TrayPassObject.trayPass.showInfo("Downloaded " + ToolFile.formatSize(fileSize) + " to " + fileName + " (" + ToolFile.formatSize(fileSize) + ")");
				bos.close();
				bis.close();
				sock.close();
			} else if (server.equals(action)) {
				ServerSocket servsock = new ServerSocket(port);
				File file = new File(path);
				while (true && !interpreter.isStop()) {
					TrayPassObject.trayPass.showInfo("Sharing " + path + " (" + ToolFile.formatSize(file.length()) + ")" + " on port " + port);
					Socket sock = servsock.accept();
					TrayPassObject.trayPass.showInfo("Sending " + path + " to " + sock.toString());
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
					OutputStream os = sock.getOutputStream();
					BufferedOutputStream bos = new BufferedOutputStream(os);
					bos.write(getBufferArray(file.getName(), bufferSize), 0, bufferSize);
					bos.write(getBufferArray(file.length() + "", bufferSize), 0, bufferSize);
					while ((in = bis.read(buffer)) != -1 && !interpreter.isStop()) {
						bos.write(buffer, 0, in);
					}
					bos.close();
					bis.close();
					os.flush();
					sock.close();
				}
			}
		} catch (Exception e) {
			logger.error(e);
			TrayPassObject.trayPass.showError(e.getMessage());
			result = null;
		}
		return result;
	}

	public static byte[] getBufferArray(String str, int size) {
		byte[] result = new byte[size];
		for (int i = 0; i < str.getBytes().length; i++) {
			result[i] = str.getBytes()[i];
		}
		return result;
	}
}