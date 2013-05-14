package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;
import traypass.syntax.Interpreter;

public class ActionAnd extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = Function.boolTrue;
		for (String logic : parameters) {
			if (!Interpreter.isTrue(executeParam(logic))) {
				result = Function.boolFalse;
				break;
			}
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}