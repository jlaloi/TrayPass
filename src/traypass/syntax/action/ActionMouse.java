package traypass.syntax.action;

import traypass.syntax.Action;
import traypass.tools.ToolMouse;

public class ActionMouse extends Action {

	public String execute(Object... parameter) {
		int x = Integer.valueOf((String) parameter[0]);
		int y = Integer.valueOf((String) parameter[0]);
		int click = Integer.valueOf((String) parameter[0]);
		ToolMouse.doClick(x, y, click);
		return "";
	}

}