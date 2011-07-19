package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.tools.ToolMouse;

public class ActionMouse extends Action {

	public String execute(List<String> parameters) {
		int x = Integer.valueOf(parameters.get(0));
		int y = Integer.valueOf(parameters.get(1));
		int click = Integer.valueOf(parameters.get(2));
		ToolMouse.doClick(x, y, click);
		return "";
	}

}