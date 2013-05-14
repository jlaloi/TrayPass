package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;

public class ActionStartsWith extends Action {

	public String doAction(List<String> parameters) {
		String result = Function.boolFalse;
		int offset = 0;
		if (parameters.size() > 2) {
			offset = Integer.valueOf(parameters.get(2));
		}
		if (parameters.get(0).startsWith(parameters.get(1), offset)) {
			result = Function.boolTrue;
		}
		return result;
	}

}