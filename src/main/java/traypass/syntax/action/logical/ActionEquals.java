package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Syntax;

public class ActionEquals extends Action {

	public String doAction(List<String> parameters) {
		String result = Syntax.boolFalse;
		String a = parameters.get(0);
		String b = parameters.get(1);
		if (a == null && b == null) {
			result = Syntax.boolTrue;
		} else if (a != null && a.equals(b)) {
			result = Syntax.boolTrue;
		}
		return result;
	}

}