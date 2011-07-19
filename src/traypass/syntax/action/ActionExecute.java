package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionExecute extends Action {

	public String execute(List<String> parameters) {
		String[] cmdArray = new String[parameters.size()];
		for (int i = 0; i < parameters.size(); i++) {
			cmdArray[i] = parameters.get(i);
		}
		execute(cmdArray);
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