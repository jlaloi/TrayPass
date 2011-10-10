package traypass.syntax.action;

import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.tools.ToolJDBC;

public class ActionJDBC extends Action {

	public static String update = "update";

	public static String select = "select";

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
			if (update.equals(action)) {
				toolJdbc.executeUpdate(query);
			} else {
				for (String row : toolJdbc.executeQuery(query)) {
					result += row + TrayPassObject.lineSeparator;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			toolJdbc.close();
		}
		return result;
	}
}