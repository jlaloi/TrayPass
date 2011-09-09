package traypass.syntax.action;

import java.util.Calendar;
import java.util.List;

import traypass.syntax.Action;

public class ActionDate extends Action {

	public String doAction(List<String> parameters) {
		return Calendar.getInstance().getTime() + "";
	}
}