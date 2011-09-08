package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;

public class ActionSwitch extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = null;
		String test;
		String value = executeParam(parameters.get(0));
		for (int i = 1; i + 1 < parameters.size();) {
			test = executeParam(parameters.get(i));
			if (value.equals(test)) {
				result = executeParam(parameters.get(i + 1));
				break;
			}
			i += 2;
		}
		if (result == null && parameters.size() % 2 == 0) {
			result = executeParam(parameters.get(parameters.size() - 1));
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}