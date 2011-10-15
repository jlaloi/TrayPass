package traypass.syntax.action.str;

import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionCount extends Action {

	public String doAction(List<String> parameters) {
		String separator = TrayPassObject.lineSeparator;
		if (parameters.size() > 1) {
			separator = parameters.get(1);
		}
		return parameters.get(0).split(separator).length - 1 + "";
	}

}