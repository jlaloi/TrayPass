package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionReplace extends Action {

	public String execute(List<String> parameters) {
		return parameters.get(0).replace(parameters.get(1), parameters.get(2));
	}
}