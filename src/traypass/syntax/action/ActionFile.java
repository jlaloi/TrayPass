package traypass.syntax.action;

import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionFile extends Action {

	public String execute(Object... parameter) {
		return ToolFile.getFileContent((String) parameter[0]);
	}

}