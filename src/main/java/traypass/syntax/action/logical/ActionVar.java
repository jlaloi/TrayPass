package traypass.syntax.action.logical;

import java.util.HashMap;
import java.util.List;

import traypass.syntax.Action;

public class ActionVar extends Action {

	private static HashMap<String, String> vars = new HashMap<String, String>();

	public String doAction(List<String> parameters) {
		String result = null;
		if (parameters.size() == 1) {
			result = get(parameters.get(0));
		} else if (parameters.size() == 2) {
			result = set(parameters.get(0), parameters.get(1));
		}
		return result;
	}

	public static String get(String varName) {
		String result = null;
		if (vars.containsKey(varName)) {
			result = vars.get(varName);
		}
		return result;
	}

	public static String set(String varName, String value) {
		vars.put(varName, value);
		return value;
	}

	public static void clear() {
		vars.clear();
	}

}