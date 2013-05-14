package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;
import traypass.syntax.Interpreter;

public class ActionOr extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = Function.boolFalse;
		for (String logic : parameters) {
			if (Interpreter.isTrue(executeParam(logic))) {
				result = Function.boolTrue;
				break;
			}
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}