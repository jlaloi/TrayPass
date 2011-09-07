package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionIndexOf extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		String in = parameters.get(0);
		String what = parameters.get(1);
		int fromIndex = 0;
		if (parameters.size() > 2) {
			fromIndex = Integer.valueOf(parameters.get(2));
		}
		result = in.indexOf(what, fromIndex) + "";
		return result;
	}

}