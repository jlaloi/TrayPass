package traypass.syntax.action;

import java.util.Calendar;
import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionNote extends Action {

	public String doAction(List<String> parameters) {
		String file = parameters.get(0);
		String text = parameters.get(1);
		String srt = "----------- " + Calendar.getInstance().getTime() + " ----------- " + TrayPassObject.lineSeparator;
		srt += text + TrayPassObject.lineSeparator + TrayPassObject.lineSeparator;
		ToolFile.addToFile(file, srt);
		return "";
	}
}