package traypass.syntax.action;

import java.io.File;
import java.util.List;

import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionFile extends Action {

	public static final String delete = "delete";

	public static final String copy = "copy";

	public static final String move = "move";

	public String doAction(List<String> parameters) {
		String result = "";
		String action = parameters.get(0);
		File file = new File(parameters.get(1));
		if (file.exists() && file.isFile()) {
			if (move.equals(action) && !file.renameTo(new File(parameters.get(2)))) {
				result = null;
			} else if (copy.equals(action) && !ToolFile.copyFile(parameters.get(1), parameters.get(2))) {
				result = null;
			} else if (delete.equals(action) && !file.delete()) {
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}

}