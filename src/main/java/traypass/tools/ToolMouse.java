package traypass.tools;

import java.awt.event.InputEvent;

import traypass.ressources.Factory;
import traypass.syntax.action.ActionWait;

public class ToolMouse {

	public static int getMouseClick(int click) {
		int result = 0;
		switch (click) {
		case 1:
		case 4:
			result = InputEvent.BUTTON1_MASK;
			break;
		case 2:
			result = InputEvent.BUTTON2_MASK;
			break;
		case 3:
			result = InputEvent.BUTTON3_MASK;
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}

	public static void doClick(int x, int y, int click) {
		int c = getMouseClick(click);
		if (c > 0) {
			System.out.println("click " + x + "x" + y + " " + c);
			Factory.getRobot().mouseMove(x, y);
			Factory.getRobot().mousePress(c);
			Factory.getRobot().mouseRelease(c);
			if (click > 3) {
				ActionWait.waitMS(200);
				Factory.getRobot().mousePress(c);
				Factory.getRobot().mouseRelease(c);
			}
		}
	}

}