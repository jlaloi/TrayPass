package traypass.syntax.action;

import java.util.List;

import traypass.ressources.Factory;
import traypass.syntax.Action;

public class ActionInfo extends Action {

	public String doAction(List<String> parameters) {
		String str = "";
		if (parameters.size() > 0) {
			str = parameters.get(0);
		}
		Factory.trayPass.showInfo(str);
		return str;
	}
}