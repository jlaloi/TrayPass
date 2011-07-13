package traypass.syntax.action;

import traypass.syntax.Action;
import traypass.tools.ToolClipboard;

public class ActionSetClipboard extends Action {

	public String execute(Object... parameter) {
		ToolClipboard.setClipboard((String) parameter[0]);
		return "";
	}

}