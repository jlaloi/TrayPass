package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;
import traypass.tools.ToolFTP;

public class ActionFTP extends Action {

	public static final String upload = "upload";
	public static final String download = "download";

	public String doAction(List<String> parameters) {
		String result = null;
		String host = parameters.get(0);
		int port = Integer.valueOf(parameters.get(1));
		String user = parameters.get(2);
		String password = parameters.get(3);
		String action = parameters.get(4);
		String serverFileName = parameters.get(5);
		String localFileName = parameters.get(6);
		ToolFTP ftp = new ToolFTP(host, port, user, password);
		if (upload.equalsIgnoreCase(action)) {
			if (ftp.uploadFile(serverFileName, localFileName)) {
				result = Function.boolTrue;
			} else {
				result = Function.boolFalse;
			}
		} else if (download.equalsIgnoreCase(action)) {
			if (ftp.downloadFile(serverFileName, localFileName)) {
				result = Function.boolTrue;
			} else {
				result = Function.boolFalse;
			}
		}
		return result;
	}
}