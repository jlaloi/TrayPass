package traypass.syntax.action.logical;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Syntax;

public class ActionIs extends Action {

	public static String file = "file";
	public static String directory = "directory";

	public String doAction(List<String> parameters) {
		String result = Syntax.boolFalse;
		String test = parameters.get(0);
		String filePath = parameters.get(1);
		File f = new File(filePath);
		if (f.exists()) {
			if (file.equals(test) && f.isFile()) {
				result = Syntax.boolTrue;
			} else if (directory.equals(test) && f.isDirectory()) {
				result = Syntax.boolTrue;
			}
		}
		return result;
	}

}