package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;

public class ActionWhile extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		while (!interpreter.isStop() && Interpreter.isTrue(executeParam(parameters.get(0)))) {
			result = executeParam(parameters.get(1));
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}