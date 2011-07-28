package traypass.syntax;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {

	public String execute(List<String> parameters) {
		String result = "";
		List<String> computedParams = new ArrayList<String>();
		for (String param : parameters) {
			computedParams.add(executeParam(param));
		}
		result = doAction(computedParams);
		return result;
	}

	protected String executeParam(String param) {
		return Interpreter.clearEscapeChar(Interpreter.computeFunction(param));
	}

	public abstract String doAction(List<String> parameters);

	public static String[] listToArray(List<String> parameters) {
		String[] cmdArray = new String[parameters.size()];
		for (int i = 0; i < parameters.size(); i++) {
			cmdArray[i] = parameters.get(i);
		}
		return cmdArray;
	}

}