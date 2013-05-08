package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionMath extends Action {

	public String doAction(List<String> parameters) {
		double result = 0;
		String op = parameters.get(0);
		double a = Double.valueOf(parameters.get(1));
		double b = Double.valueOf(parameters.get(2));
		if ("+".equals(op)) {
			result = a + b;
		} else if ("-".equals(op)) {
			result = a - b;
		} else if ("*".equals(op)) {
			result = a * b;
		} else if ("/".equals(op)) {
			result = a / b;
		} else if ("%".equals(op)) {
			result = a % b;
		}
		return result + "";
	}

}