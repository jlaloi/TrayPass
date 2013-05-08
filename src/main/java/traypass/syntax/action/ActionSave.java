package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.tools.ToolFile;

public class ActionSave extends Action {

	public String doAction(List<String> parameters) {
		String file = parameters.get(0);
		String text = parameters.get(1);
		String append = parameters.get(2);
		ToolFile.addToFile(file, text, Interpreter.isTrue(append));
		return text;
	}
}