package traypass.syntax.action;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;

public class ActionExecute extends Action {

	public String doAction(List<String> parameters) {
		return execute(parameters).toString();
	}

	public static Process execute(List<String> parameters) {
		Process p = null;
		try {
			if (parameters.size() == 1) {
				p = Runtime.getRuntime().exec(parameters.get(0));
			} else if (parameters.size() == 2) {
				p = Runtime.getRuntime().exec(new String[] { parameters.get(0), parameters.get(1) });
			} else if (parameters.size() == 3) {
				p = Runtime.getRuntime().exec(new String[] { parameters.get(0), parameters.get(1) }, new String[] {}, new File(parameters.get(2)));
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return p;
	}

}