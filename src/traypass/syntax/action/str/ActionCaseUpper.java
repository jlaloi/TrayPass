package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionCaseUpper extends Action {

	public String doAction(List<String> parameters) {
		String result = parameters.get(0).toUpperCase();
		return result;
	}

}