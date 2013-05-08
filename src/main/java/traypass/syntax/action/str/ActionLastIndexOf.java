package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;

public class ActionLastIndexOf extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		String in = parameters.get(0);
		String what = parameters.get(1);
		int fromIndex = 0;
		if (parameters.size() > 2) {
			fromIndex = Integer.valueOf(parameters.get(2));
		}
		result = in.lastIndexOf(what, fromIndex) + "";
		return result;
	}

}