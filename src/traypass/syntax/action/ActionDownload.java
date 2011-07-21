package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionDownload extends Action {

	public String execute(List<String> parameters) {
		String result = "";
		String url = parameters.get(0);
		String file = parameters.get(1);
		ToolFile.downloadFile(url, file);
		return result;
	}

}
