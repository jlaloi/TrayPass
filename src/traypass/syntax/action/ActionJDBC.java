package traypass.syntax.action;

import java.util.List;

import org.apache.log4j.Logger;

import traypass.TrayPassObject;
import traypass.log.LogFactory;
import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.tools.ToolJDBC;

public class ActionJDBC extends Action {

	private static final Logger logger = LogFactory.getLogger(ActionJDBC.class);

	public static String update = "update";

	public static String select = "select";

	public static String script = "script";

	public String doAction(List<String> parameters) {
		String result = "";
		String driver = parameters.get(0);
		String url = parameters.get(1);
		String login = parameters.get(2);
		String password = parameters.get(3);
		String action = parameters.get(4);
		String query = parameters.get(5);
		ToolJDBC toolJdbc = new ToolJDBC(driver, url, login, password);
		if (parameters.size() > 6) {
			toolJdbc.setColSeparator(parameters.get(6));
		}
		try {
			toolJdbc.connect();
			if (script.equals(action)) {
				for (String row : toolJdbc.executeScript(query)) {
					result += row + TrayPassObject.lineSeparator;
				}
			} else if (update.equals(action)) {
				toolJdbc.executeUpdate(query);
			} else {
				for (String row : toolJdbc.executeQuery(query)) {
					result += row + TrayPassObject.lineSeparator;
				}
			}
		} catch (Exception e) {
			Interpreter.showError(e.getMessage());
			logger.error(e);
		} finally {
			toolJdbc.close();
		}
		return result;
	}
}