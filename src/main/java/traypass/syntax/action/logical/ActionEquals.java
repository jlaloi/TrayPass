package traypass.syntax.action.logical;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;

public class ActionEquals extends Action {

	public String doAction(List<String> parameters) {
		String result = Function.boolFalse;
		String a = parameters.get(0);
		String b = parameters.get(1);
		if (a == null && b == null) {
			result = Function.boolTrue;
		} else if (a != null && a.equals(b)) {
			result = Function.boolTrue;
		}
		return result;
	}

}