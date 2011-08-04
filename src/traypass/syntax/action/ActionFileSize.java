package traypass.syntax.action;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionFileSize extends Action {

	public String doAction(List<String> parameters) {
		return ToolFile.formatSize(new File(parameters.get(0)).length());
	}
}