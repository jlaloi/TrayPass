package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;

public class ActionNot extends Action {

	public String doAction(List<String> parameters) {
		String result = null;
		if (Function.boolTrue.equals(parameters.get(0))) {
			result = Function.boolFalse;
		} else if (Function.boolFalse.equals(parameters.get(0))) {
			result = Function.boolTrue;
		}
		return result;
	}

}