package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;

public class ActionWait extends Action {

	public String execute(List<String> parameters) {
		try {
			long ms = Long.valueOf(parameters.get(0));
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