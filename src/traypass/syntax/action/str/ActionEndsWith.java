package traypass.syntax.action.str;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Syntax;

public class ActionEndsWith extends Action {

	public String doAction(List<String> parameters) {
		String result = Syntax.boolFalse;
		if (parameters.get(0).endsWith(parameters.get(1))) {
			result = Syntax.boolTrue;
		}
		return result;
	}

}