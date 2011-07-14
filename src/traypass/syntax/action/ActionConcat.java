package traypass.syntax.action;

import traypass.syntax.Action;

public class ActionConcat extends Action {

	public String execute(Object... parameter) {
		String result = "";
		String[] texts = getStringArray(parameter);
		for (String text : texts) {
			result += text;
		}
		return result;
	}

}