package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionExecute extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		try {
			Runtime.getRuntime().exec(listToArray(parameters));
		} catch (Exception exp) {
			exp.printStackTrace();
			result = null;
		}
		return result;
	}

}