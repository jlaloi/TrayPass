package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Syntax;

public class ActionNot extends Action {

	public String doAction(List<String> parameters) {
		String result = null;
		if (Syntax.boolTrue.equals(parameters.get(0))) {
			result = Syntax.boolFalse;
		} else if (Syntax.boolFalse.equals(parameters.get(0))) {
			result = Syntax.boolTrue;
		}
		return result;
	}

}