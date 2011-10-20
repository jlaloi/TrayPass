package traypass.syntax.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionExecuteResult extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		try {
			String line;
			Process p;
			if (parameters.size() == 1) {
				p = Runtime.getRuntime().exec(parameters.get(0));
			} else {
				p = Runtime.getRuntime().exec(parameters.get(0), new String[] {}, new File(parameters.get(1)));
			}
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), TrayPassObject.consoleEncode));
			while ((line = input.readLine()) != null) {
				result += line + TrayPassObject.lineSeparator;
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
			result = null;
		}
		return result;
	}

}