package traypass.syntax.action;

import java.util.Calendar;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionNote extends Action {

	public String execute(Object... parameter) {
		String file = (String) parameter[0];
		String text = (String) parameter[1];
		String srt = "----------- " + Calendar.getInstance().getTime() + " ----------- " + TrayPassObject.lineSeparator;
		srt += text + TrayPassObject.lineSeparator + TrayPassObject.lineSeparator;
		ToolFile.addToFile(file, srt);
		return "";
	}

}