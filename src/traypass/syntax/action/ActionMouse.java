package traypass.syntax.action;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionMouse extends Action {

	public String execute(Object... parameter) {
		int x = Integer.valueOf((String) parameter[0]);
		int y = Integer.valueOf((String) parameter[0]);
		int click = Integer.valueOf((String) parameter[0]);
		TrayPassObject.getRobot().mouseMove(x,y);
		TrayPassObject.getRobot().mousePress(click);
		TrayPassObject.getRobot().mouseRelease(click);
		return "";
	}

}