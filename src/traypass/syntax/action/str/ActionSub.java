package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionSub extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		int start = Integer.valueOf(parameters.get(1));
		int end = Integer.valueOf(parameters.get(2));
		result = parameters.get(0).substring(start, end);
		return result;
	}

}