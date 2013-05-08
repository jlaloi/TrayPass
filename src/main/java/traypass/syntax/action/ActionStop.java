package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionStop extends Action {

	public String doAction(List<String> parameters) {
		interpreter.setStop(true);
		return null;
	}
}