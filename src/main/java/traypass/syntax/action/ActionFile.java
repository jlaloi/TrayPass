package traypass.syntax.action;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Syntax;
import traypass.tools.ToolFile;

public class ActionFile extends Action {

	public static final String delete = "delete";

	public static final String copy = "copy";

	public static final String move = "move";

	public static final String exist = "exist";

	public String doAction(List<String> parameters) {
		String result = null;
		String action = parameters.get(0);
		File file = new File(parameters.get(1));
		if (file.exists()) {
			if (move.equals(action) && file.renameTo(new File(parameters.get(2)))) {
				result = Syntax.boolTrue;
			} else if (copy.equals(action) && file.isFile() && ToolFile.copyFile(parameters.get(1), parameters.get(2))) {
				result = Syntax.boolTrue;
			} else if (copy.equals(action) && file.isDirectory() && ToolFile.copyDir(parameters.get(1), parameters.get(2))) {
				result = Syntax.boolTrue;
			} else if (delete.equals(action) && file.delete()) {
				result = Syntax.boolTrue;
			} else if (exist.equals(action)) {
				result = Syntax.boolTrue;
			}
		}
		return result;
	}

}