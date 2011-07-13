package traypass.syntax.action;

import traypass.syntax.Action;

public class ActionExecute extends Action {

	public String execute(Object... parameter) {
		execute(getStringArray(parameter));
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