package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionSplit extends Action {

	public String doAction(List<String> parameters) {
		String str = parameters.get(0);
		String separator = parameters.get(1);
		int count = 0;
		if (parameters.size() > 2) {
			count = Integer.valueOf(parameters.get(2));
		}
		return str.split(separator)[count];
	}

}