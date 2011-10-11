package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionLength extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		result = parameters.get(0).length() + "";
		return result;
	}

}