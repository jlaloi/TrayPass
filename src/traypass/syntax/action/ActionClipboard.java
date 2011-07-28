package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.tools.ToolClipboard;

public class ActionClipboard extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		if (parameters != null && parameters.size() == 1) {
			ToolClipboard.setClipboard(parameters.get(0));
		} else {
			result = ToolClipboard.getClipboardContent();
		}
		return result;
	}

}