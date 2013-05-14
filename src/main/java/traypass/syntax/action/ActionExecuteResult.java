package traypass.syntax.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import traypass.ressources.Factory;
import traypass.syntax.Action;

public class ActionExecuteResult extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		try {
			Process p = ActionExecute.execute(parameters);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), Factory.get().getConfig().getConsoleEncode()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line + Factory.lineSeparator;
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
			result = null;
		}
		return result;
	}

}