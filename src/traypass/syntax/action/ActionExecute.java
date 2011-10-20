package traypass.syntax.action;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;

public class ActionExecute extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		try {
			if (parameters.size() == 1) {
				Runtime.getRuntime().exec(parameters.get(0));
			} else {
				Runtime.getRuntime().exec(parameters.get(0), new String[] {}, new File(parameters.get(1)));
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			result = null;
		}
		return result;
	}

}