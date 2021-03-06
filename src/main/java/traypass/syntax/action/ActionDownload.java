package traypass.syntax.action;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;
import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.tools.ToolDownload;
import traypass.tools.ToolFile;

public class ActionDownload extends Action {

	private static final Logger logger = LoggerFactory.getLogger(ActionDownload.class);

	public String doAction(List<String> parameters) {
		String result = "";
		String url = parameters.get(0);
		String file = parameters.get(1);
		InputStream input = null;
		FileOutputStream writeFile = null;
		long fullSize = 0;
		long currentSize = 0;
		long lastDl = 0;
		long progress = 0;
		try {
			URLConnection connection = ToolDownload.getURConnection(url);
			fullSize = connection.getContentLength();
			input = connection.getInputStream();
			writeFile = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int read;
			while ((read = input.read(buffer)) > 0 && !interpreter.isStop()) {
				writeFile.write(buffer, 0, read);
				currentSize += read;
				progress = currentSize * 100 / fullSize;
				if (Factory.get().getTrayPass() != null && progress % 10 == 0 && progress != lastDl && progress > 0) {
					Factory.get().getTrayPass().showInfo("Downloading " + ToolFile.formatSize(fullSize) + " from " + url + " (" + progress + "%)");
					lastDl = progress;
				}
			}
			writeFile.flush();
		} catch (Exception e) {
			Interpreter.showError("Error while downloading " + url + ": " + e);
			logger.error("Error", e);
			result = null;
		} finally {
			try {
				if (writeFile != null) {
					writeFile.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				result = null;
				logger.error("Error", e);
			}
		}
		return result;
	}

}
