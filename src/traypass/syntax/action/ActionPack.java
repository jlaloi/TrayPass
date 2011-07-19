package traypass.syntax.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.tools.ToolFile;

public class ActionPack extends Action {

	public static String paramPattern = "#param#";

	public static String lineFile = "line.txt";

	public String execute(List<String> parameters) {
		String pack = parameters.get(0);
		HashMap<String, String> files = preparePack(pack);
		String result = "";
		if (files.containsKey(lineFile)) {
			String[] lines = ToolFile.getFileLines(files.get(lineFile)).get(0).split(paramPattern);
			// Parameters
			if (lines.length > 1 && parameters.size() > 1) {
				for (int i = 0; i < lines.length; i++) {
					result += lines[i];
					if (i + 1 != lines.length && i < parameters.size() - 1) {
						result += parameters.get(i + 1);
					}
				}
			} else {
				result = lines[0];
			}
			// Files
			for (String file : files.keySet()) {
				if (!file.equals(lineFile)) {
					result = result.replace(file, files.get(file));
				}
			}
			System.out.println("pack:" + result);
			Interpreter.computeFunctions(result);
		}
		return "";
	}

	private HashMap<String, String> preparePack(String path) {
		HashMap<String, String> result = new HashMap<String, String>();
		int BUFFER = 2048;
		try {
			String tempDir = getTmpDir(getFileName(path));
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream buffi = new BufferedInputStream(fis);
			ZipInputStream zip = new ZipInputStream(buffi);
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					int count;
					byte data[] = new byte[BUFFER];
					String destName = tempDir + getFileName(entry.getName());
					FileOutputStream fos = new FileOutputStream(destName);
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zip.read(data, 0, BUFFER)) != -1)
						dest.write(data, 0, count);
					dest.flush();
					dest.close();
					result.put(getFileName(destName), destName);
				}
			}
			zip.close();
		} catch (Exception e) {
			Interpreter.showError("preparePack: " + path + "\n" + e);
			e.printStackTrace();
		}
		return result;
	}

	private String getFileName(String file) {
		return new File(file).getName();
	}

	private String getTmpDir(String path) {
		String result;
		if (System.getenv("TMP") != null && System.getenv("TMP").trim().length() > 0) {
			result = System.getenv("TMP") + TrayPassObject.fileSeparator + TrayPassObject.tmpDir + TrayPassObject.fileSeparator
					+ path + TrayPassObject.fileSeparator;
		} else {
			result = "/tmp" + TrayPassObject.fileSeparator + TrayPassObject.tmpDir + TrayPassObject.fileSeparator + path
					+ TrayPassObject.fileSeparator;
		}
		File tmp = new File(result);
		if (!tmp.exists() || !tmp.isDirectory()) {
			tmp.mkdirs();
		}
		return result;
	}

}