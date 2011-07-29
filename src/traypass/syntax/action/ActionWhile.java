package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Syntax;

public class ActionWhile extends Action {

	public String execute(List<String> parameters) {
		String result = "";
		String test = executeParam(parameters.get(0));
		while (Syntax.boolTrue.equals(test)) {
			result = executeParam(parameters.get(1));
			test = executeParam(parameters.get(0));
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}