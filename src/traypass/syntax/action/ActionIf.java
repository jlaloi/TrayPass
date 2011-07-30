package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.syntax.Syntax;

public class ActionIf extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		String test = executeParam(parameters.get(0));
		if (Syntax.isTrue(test)) {
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