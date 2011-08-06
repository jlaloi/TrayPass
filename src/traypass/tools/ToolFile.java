package traypass.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Interpreter;

public class ToolFile {

	public static List<String> getFileLines(String file) {
		List<String> result = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, TrayPassObject.fileEncode));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			in.close();
		} catch (Exception e) {
			Interpreter.showError("getFileLines " + file + ":\n" + e);
			e.printStackTrace();
		}
		return result;
	}

	public static String getFileContent(String file) {
		String result = "";
		for (String line : getFileLines(file)) {
			result += line + TrayPassObject.lineSeparator;
		}
		return result;
	}

	public static void addToFile(String file, String str, boolean append) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file, append));
			bw.write(str);
			bw.flush();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception ioe2) {
				ioe2.printStackTrace();
			}
		}
	}

	public static String formatSize(double size) {
		double d1024 = new Double(1024);
		NumberFormat myformat = NumberFormat.getInstance();
		myformat.setMaximumFractionDigits(2);
		myformat.setMinimumFractionDigits(2);
		if (size < d1024) {
			return myformat.format(size) + " Byte";
		}
		size /= d1024;
		if (size < 1024) {
			return myformat.format(size) + " Ko";
		}
		size /= d1024;
		if (size < 1024) {
			return myformat.format(size) + " Mo";
		}
		size /= d1024;
		if (size < 1024) {
			return myformat.format(size) + " Go";
		}
		size /= d1024;
		return myformat.format(size) + " To";
	}

	public static boolean copyFile(String currentFile, String newFile) {
		boolean result = true;
		int bufferSize = 2048;
		System.out.println("Copying " + currentFile + " to " + newFile);
		File file = new File(newFile);
		if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
			return false;
		}
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(currentFile);
			out = new FileOutputStream(newFile);
			byte[] buffer = new byte[bufferSize];
			int nbRead;
			while ((nbRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, nbRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public static boolean copyDir(String source, String destination) {
		boolean result = false;
		File src = new File(source);
		File dest = new File(destination);
		if (src.exists() && src.isDirectory()) {
			if (!dest.exists() && !dest.mkdirs()) {
				return false;
			}
			for (File file : src.listFiles()) {
				if (!ignoreFile(file.getName())) {
					if (file.isFile() && !copyFile(file.getAbsolutePath(), dest.getAbsolutePath() + TrayPassObject.fileSeparator + file.getName())) {
						return false;
					} else if (file.isDirectory() && !copyDir(file.getAbsolutePath(), destination + TrayPassObject.fileSeparator + file.getParentFile().getName())) {
						return false;
					}
				}
			}
			result = true;
		}
		return result;
	}

	public static boolean ignoreFile(String file) {
		return file.endsWith("RECYCLE.BIN") || file.endsWith("RECYCLER") || file.endsWith("System Volume Information") || file.endsWith("desktop.ini");
	}

	public static String getTmpDir() {
		String result;
		if (System.getenv("TMP") != null && System.getenv("TMP").trim().length() > 0) {
			result = System.getenv("TMP") + TrayPassObject.fileSeparator + TrayPassObject.tmpDir + TrayPassObject.fileSeparator;
		} else {
			result = "/tmp" + TrayPassObject.fileSeparator + TrayPassObject.tmpDir + TrayPassObject.fileSeparator;
		}
		return result;
	}

}