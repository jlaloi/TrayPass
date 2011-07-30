package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.syntax.Syntax;

public class ActionWhile extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		String test = executeParam(parameters.get(0));
		while (Syntax.boolTrue.equals(test) && !interpreter.isStop()) {
			result = executeParam(parameters.get(1));
			test = executeParam(parameters.get(0));
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}