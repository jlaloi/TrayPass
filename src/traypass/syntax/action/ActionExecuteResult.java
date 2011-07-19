package traypass.syntax.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionExecuteResult extends Action {

	public String execute(List<String> parameters) {
		String result = execute(listToArray(parameters));
		return result;
	}

	public static String execute(String[] cmdArray) {
		String result = "";
		try {
			String line;
			Process p = Runtime.getRuntime().exec(cmdArray);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), TrayPassObject.consoleEncode));
			while ((line = input.readLine()) != null) {
				result += line + TrayPassObject.lineSeparator;
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return result;
	}

}