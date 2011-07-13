package traypass.syntax.action;

import traypass.syntax.Action;
import traypass.tools.ToolClipboard;

public class ActionClipboard extends Action {

	public String execute(Object... parameter) {
		String result = "";
		if (parameter != null && parameter.length == 1){
			ToolClipboard.setClipboard((String) parameter[0]);
		}else{
			result = ToolClipboard.getClipboardContent();
		}
		return result;
	}

}