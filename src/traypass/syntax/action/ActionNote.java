package traypass.syntax.action;

import java.util.Calendar;
import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.tools.ToolFile;

public class ActionNote extends Action {

	public String doAction(List<String> parameters) {
		String file = parameters.get(0);
		String text = parameters.get(1);
		String append = parameters.get(2);
		String srt = "";
		if (Interpreter.isTrue(append)) {
			srt = "----------- " + Calendar.getInstance().getTime() + " ----------- " + TrayPassObject.lineSeparator;
			srt += text + TrayPassObject.lineSeparator;
		} else {
			srt = text;
		}
		ToolFile.addToFile(file, srt, Interpreter.isTrue(append));
		return "";
	}
}