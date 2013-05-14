package traypass.syntax.action.logical;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Function;

public class ActionIs extends Action {

	public static final String file = "file";
	public static final String directory = "directory";

	public String doAction(List<String> parameters) {
		String result = Function.boolFalse;
		String test = parameters.get(0);
		String filePath = parameters.get(1);
		File f = new File(filePath);
		if (f.exists()) {
			if (file.equals(test) && f.isFile()) {
				result = Function.boolTrue;
			} else if (directory.equals(test) && f.isDirectory()) {
				result = Function.boolTrue;
			}
		}
		return result;
	}

}