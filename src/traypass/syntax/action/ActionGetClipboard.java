package traypass.syntax.action;

import traypass.syntax.Action;
import traypass.tools.ToolClipboard;

public class ActionGetClipboard extends Action {

	public String execute(Object... parameter) {
		return ToolClipboard.getClipboardContent();
	}

}