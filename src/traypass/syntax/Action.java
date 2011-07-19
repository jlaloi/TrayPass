package traypass.syntax;

import java.util.List;

public abstract class Action {

	public abstract String execute(List<String> parameters);

	public static String[] listToArray(List<String> parameters) {
		String[] cmdArray = new String[parameters.size()];
		for (int i = 0; i < parameters.size(); i++) {
			cmdArray[i] = parameters.get(i);
		}
		return cmdArray;
	}

}