package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionTrim extends Action {

	public String doAction(List<String> parameters) {
		return parameters.get(0).trim();
	}
}