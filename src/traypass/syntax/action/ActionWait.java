package traypass.syntax.action;

import java.util.List;

import org.apache.log4j.Logger;

import traypass.log.LogFactory;
import traypass.syntax.Action;

public class ActionWait extends Action {

	private static final Logger logger = LogFactory.getLogger(ActionWait.class);

	public String doAction(List<String> parameters) {
		try {
			long ms = Long.valueOf(parameters.get(0));
			System.out.println("Wait for " + ms + " ms");
			waitMS(ms);
		} catch (Exception e) {
			logger.error(e);
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