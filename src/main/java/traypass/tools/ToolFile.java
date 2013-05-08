package traypass.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;
import traypass.syntax.Interpreter;

public class ToolFile {

	private static final Logger logger = LoggerFactory.getLogger(ToolFile.class);

	public static List<String> getFileLines(String file) {
		List<String> result = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, Factory.fileEncode));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			in.close();
		} catch (Exception e) {
			Interpreter.showError("getFileLines " + file + ":\n" + e);
			logger.error("Error", e);
		}
		return result;
	}

	public static List<String> getFileLinesJar(String file) {
		List<String> result = new ArrayList<String>();
		try {
			InputStream in = Factory.class.getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, Factory.fileEncode));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			in.close();
		} catch (Exception e) {
			Interpreter.showError("getFileLinesJar " + file + ":\n" + e);
			logger.error("Error", e);
		}
		return result;
	}

	public static String getFileContent(String file) {
		String result = "";
		for (String line : getFileLines(file)) {
			result += line + Factory.lineSeparator;
		}
		return result;
	}

	public static void addToFile(String file, String str, boolean append) {
		try {
			List<String> save = new ArrayList<String>();
			if (append) {
				save = getFileLines(file);
			}
			OutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			OutputStreamWriter out = new OutputStreamWriter(bos, Factory.fileEncode);
			for (String line : save) {
				out.write(line + Factory.lineSeparator);
			}
			for (String line : str.split(Factory.lineSeparator)) {
				out.write(line + Factory.lineSeparator);
			}
			out.flush();
			out.close();
		} catch (Exception ioe) {
			logger.error("Error", ioe);
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
			logger.error("Error", e);
			result = false;
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				logger.error("Error", e);
				result = false;
			}
			try {
				in.close();
			} catch (Exception e) {
				logger.error("Error", e);
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
					if (file.isFile() && !copyFile(file.getAbsolutePath(), dest.getAbsolutePath() + Factory.fileSeparator + file.getName())) {
						return false;
					} else if (file.isDirectory() && !copyDir(file.getAbsolutePath(), destination + Factory.fileSeparator + file.getParentFile().getName())) {
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
			result = System.getenv("TMP") + Factory.fileSeparator;
		} else {
			result = Factory.fileSeparator + "tmp" + Factory.fileSeparator;
		}
		result += Factory.tmpDir + Factory.fileSeparator;

		new File(result).mkdirs();
		return result;
	}

}