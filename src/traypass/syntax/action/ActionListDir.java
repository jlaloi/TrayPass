package traypass.syntax.action;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.tools.ToolFile;

public class ActionListDir extends Action {

	public String doAction(List<String> parameters) {
		String result = "";
		for (String file : list(new File(parameters.get(0)))) {
			result += file + TrayPassObject.lineSeparator;
		}
		return result;
	}

	public static TreeSet<String> list(File f) {
		TreeSet<String> result = new TreeSet<String>();
		if (!f.exists() || f.getName().endsWith("RECYCLE.BIN") || f.getName().endsWith("RECYCLER") || f.getName().endsWith("System Volume Information"))
			return result;
		TreeSet<File> set = new TreeSet<File>();
		for (File file : f.listFiles()) {
			set.add(file);
		}
		for (File file : set) {
			if (file.isDirectory()) {
				result.addAll(list(file));
			} else {
				result.add(file.getAbsolutePath() + " - " + ToolFile.formatSize(file.length()));
			}
		}
		result.add(f.getAbsolutePath() + " - " + ToolFile.formatSize(getSize(f)));
		return result;
	}

	public static long getSize(File f) {
		long result = 0;
		if (!f.exists() || f.getName().endsWith("RECYCLE.BIN") || f.getName().endsWith("RECYCLER") || f.getName().endsWith("System Volume Information"))
			return result;
		for (File file : f.listFiles()) {
			if (file.isDirectory()) {
				result += getSize(file);
			} else {
				result += file.length();
			}
		}
		return result;
	}
}