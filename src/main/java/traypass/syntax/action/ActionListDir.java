package traypass.syntax.action;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

import traypass.ressources.Factory;
import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionListDir extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		for (String file : list(new File(parameters.get(0)))) {
			result += file + Factory.lineSeparator;
		}
		return result;
	}

	public static TreeSet<String> list(File f) {
		TreeSet<String> result = new TreeSet<String>();
		if (!f.exists() || ToolFile.ignoreFile(f.getName())) {
			return result;
		}
		result.add(f.getAbsolutePath());
		for (File file : f.listFiles()) {
			if (file.isDirectory()) {
				result.addAll(list(file));
			} else {
				result.add(file.getAbsolutePath());
			}
		}
		return result;
	}

}