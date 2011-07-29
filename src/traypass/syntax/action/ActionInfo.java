package traypass.syntax.action;

import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionInfo extends Action {

	public String doAction(List<String> parameters) {
		TrayPassObject.trayPass.showInfo(parameters.get(0));
		return "";
	}

}