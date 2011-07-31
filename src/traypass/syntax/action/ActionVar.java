package traypass.syntax.action;

import java.util.HashMap;
import java.util.List;

import traypass.syntax.Action;

public class ActionVar extends Action {

	private static HashMap<String, String> vars = new HashMap<String, String>();

	public String doAction(List<String> parameters) {
		String result = null;
		if (parameters.size() == 1 && vars.containsKey(parameters.get(0))) {
			result = vars.get(parameters.get(0));
		} else if (parameters.size() == 2) {
			result = parameters.get(1);
			vars.put(parameters.get(0), result);
		}
		return result;
	}

	public static void clear() {
		vars.clear();
	}

}