import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PackManager {

	public static String fileSeparator = System.getProperty("file.separator");

	public static String tmpDir = "TrayPass";

	public static String paramSeparator = "#";

	public static String paramPattern = "#param#";

	public static String lineFile = "line.txt";

	public static HashMap<String, String> preparePack(String path) {
		HashMap<String, String> result = new HashMap<String, String>();
		String tempDir = getTmpDir(getFileName(path));
		int BUFFER = 2048;
		try {
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
			e.printStackTrace();
		}
		return result;
	}

	public static String getFileName(String file) {
		return new File(file).getName();
	}

	public static String getTmpDir(String path) {
		String result;
		if (System.getenv("TMP") != null && System.getenv("TMP").trim().length() > 0) {
			result = System.getenv("TMP") + fileSeparator + tmpDir + fileSeparator + path + fileSeparator;
		} else {
			result = "/tmp" + fileSeparator + tmpDir + fileSeparator + path + fileSeparator;
		}
		try {
			File tmp = new File(result);
			if (!tmp.exists() || !tmp.isDirectory()) {
				tmp.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getLine(String line) {
		String result = "";
		try {
			String pack = line.substring(line.indexOf(":") + 1);
			String[] params = new String[0];
			if (pack.contains(paramSeparator)) {
				pack = pack.substring(0, pack.indexOf(paramSeparator));
				params = line.substring(line.indexOf(":") + pack.length() + 2).split(paramSeparator);
			}
			result = getLine(pack, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getLine(String pack, String[] params) {
		HashMap<String, String> files = preparePack(pack);
		String result = "";
		if (files.containsKey(lineFile)) {
			String[] lines = TrayTools.getFileLines(files.get(lineFile)).get(0).split(paramPattern);
			// Parameters
			if (lines.length > 1 && params.length > 0) {
				for (int i = 0; i < lines.length; i++) {
					result += lines[i];
					if (i + 1 != lines.length && i < params.length) {
						result += params[i];
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
		}
		return result;
	}
}