package traypass.syntax.action;

import traypass.syntax.Action;


public class ActionWait extends Action {

	public String execute(Object... parameter) {
		try {
			long ms = Long.valueOf((String) parameter[0]);
			System.out.println("Wait for " + ms + " ms");
			waitMS(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void waitMS(long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}