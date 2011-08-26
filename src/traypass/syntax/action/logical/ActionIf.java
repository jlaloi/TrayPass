package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;

public class ActionIf extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		String test = executeParam(parameters.get(0));
		if (Interpreter.isTrue(test)) {
			result = executeParam(parameters.get(1));
		} else if (parameters.size() > 2) {
			result = executeParam(parameters.get(2));
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}