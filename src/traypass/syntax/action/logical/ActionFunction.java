package traypass.syntax.action.logical;

import java.util.HashMap;
import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;

public class ActionFunction extends Action {

	private static HashMap<String, String> functions = new HashMap<String, String>();

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		String name = executeParam(parameters.get(0));
		if (parameters.size() > 1) {
			functions.put(name, parameters.get(1));
			result = name;
		} else {
			result = executeParam(functions.get(name));
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}

	public static void clear() {
		functions.clear();
	}
}