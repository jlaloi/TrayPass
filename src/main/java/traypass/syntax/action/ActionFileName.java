package traypass.syntax.action;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;

public class ActionFileName extends Action {

	public String doAction(List<String> parameters) {
		return new File(parameters.get(0)).getName();
	}
}