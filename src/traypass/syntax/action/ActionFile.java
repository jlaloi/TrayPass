package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionFile extends Action {

	public String execute(List<String> parameters) {
		return ToolFile.getFileContent(parameters.get(0));
	}

}