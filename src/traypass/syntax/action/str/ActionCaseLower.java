package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionCaseLower extends Action {

	public String doAction(List<String> parameters) {
		String result = parameters.get(0).toLowerCase();
		return result;
	}

}