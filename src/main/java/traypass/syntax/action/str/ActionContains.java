package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;

public class ActionContains extends Action {

	public String doAction(List<String> parameters) {
		String result = Function.boolFalse;
		if (parameters.get(0).contains(parameters.get(1))) {
			result = Function.boolTrue;
		}
		return result;
	}

}