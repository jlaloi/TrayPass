package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Syntax;

public class ActionContains extends Action {

	public String doAction(List<String> parameters) {
		String result = Syntax.boolFalse;
		if (parameters.get(0).contains(parameters.get(1))) {
			result = Syntax.boolTrue;
		}
		return result;
	}

}