package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionExecute extends Action {

	public String doAction(List<String> parameters) {
		execute(listToArray(parameters));
		return "";
	}

	public static void execute(String[] parameters) {
		try {
			Runtime.getRuntime().exec(parameters);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

}