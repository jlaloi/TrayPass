package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionConcat extends Action {

	public String execute(List<String> parameters) {
		String result = "";
		for (String text : parameters) {
			result += text;
		}
		return result;
	}
}