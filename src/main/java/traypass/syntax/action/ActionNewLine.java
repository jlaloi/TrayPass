package traypass.syntax.action;

import java.util.List;

import traypass.ressources.Factory;
import traypass.syntax.Action;

public class ActionNewLine extends Action {

	public String doAction(List<String> parameters) {
		return Factory.lineSeparator;
	}
}