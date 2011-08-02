package traypass.syntax.action;

import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionFileName extends Action {

	public String doAction(List<String> parameters) {
		String result = parameters.get(0);
		String[] parts = parameters.get(0).split("\\" + TrayPassObject.fileSeparator);
		if (parts.length > 0) {
			result = parts[parts.length - 1];
		}
		return result;
	}
}