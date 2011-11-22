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

import org.apache.log4j.Logger;

import traypass.TrayPassObject;
import traypass.log.LogFactory;
import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.tools.ToolFile;

public class ActionPack extends Action {
	
	private static final Logger logger = LogFactory.getLogger(ActionPack.class);

	public static String lineFile = "line.txt";

	public String doAction(List<String> parameters) {
		HashMap<String, String> files = preparePack(parameters.get(0));
		String result = null;
		if (files.containsKey(lineFile)) {
			
			// Read file
			result = "";
			for (String l : ToolFile.getFileLines(files.get(lineFile))) {
				result += l + " ";
			}
			
			// Parameters
			result = result.replace("#param0#", parameters.size() - 1 + "");		
			for(int i = 1; i < parameters.size(); i++){
				result = result.replace("#param" + i + "#", parameters.get(i));		
			}
			
			// Replace Files
			for (String file : files.keySet()) {
				if (!file.equals(lineFile)) {
					result = result.replace(file, files.get(file));
				}
			}
			
			System.out.println("pack:" + result);
			result = interpreter.computeFunctions(result);
		}
		return result;
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
			logger.error(e);
		}
		return result;
	}

	private String getFileName(String file) {
		return new File(file).getName();
	}

	private String getTmpDir(String path) {
		String result = ToolFile.getTmpDir() + path + TrayPassObject.fileSeparator;
		File tmp = new File(result);
		if (!tmp.exists() || !tmp.isDirectory()) {
			tmp.mkdirs();
		}
		return result;
	}

}