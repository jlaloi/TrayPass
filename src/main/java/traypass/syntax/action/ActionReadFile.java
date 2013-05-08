package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionReadFile extends Action {

	public String doAction(List<String> parameters) {
		return ToolFile.getFileContent(parameters.get(0));
	}

}