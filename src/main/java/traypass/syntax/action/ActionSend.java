package traypass.syntax.action;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;
import traypass.syntax.Action;
import traypass.tools.Pair;
import traypass.tools.ToolFile;

public class ActionSend extends Action {

	public static final String keyCodeString = "{code:";
	public static final int waitBetweenChar = 8;
	public static HashMap<String, int[]> lib = new HashMap<String, int[]>();
	private static final Logger logger = LoggerFactory.getLogger(ActionSend.class);

	public String doAction(List<String> parameters) {
		if (parameters.size() == 1) {
			String characters = parameters.get(0);
			for (int i = 0; i < characters.length() && !interpreter.isStop(); i++) {
				Pair<Integer, int[]> result = getCode(characters, i);
				i = result.getA();
				doType(result.getB());
			}
		} else {
			int[] toType = new int[0];
			for (String characters : parameters) {
				for (int i = 0; i < characters.length() && !interpreter.isStop(); i++) {
					Pair<Integer, int[]> result = getCode(characters, i);
					i = result.getA();
					toType = merge(toType, result.getB());
				}
			}
			doType(toType);
		}
		return "";
	}

	public static void load() {
		lib.clear();
		for (String line : ToolFile.getFileLinesJar(Factory.get().getConfig().getKeyFile())) {
			String[] parts = line.split("=>");
			if (parts.length >= 2) {
				String[] codes = parts[1].trim().split(",");
				int[] keys = new int[codes.length];
				for (int i = 0; i < codes.length; i++) {
					keys[i] = Integer.valueOf(codes[i]);
				}
				lib.put(parts[0], keys);
			}
		}
		logger.info(Factory.get().getConfig().getKeyFile() + " loaded");
	}

	public int[] getCode(String key) {
		int[] result = new int[0];
		if (lib.containsKey(key)) {
			result = lib.get(key);
		}
		return result;
	}

	private Pair<Integer, int[]> getCode(String characters, int i) {
		Pair<Integer, int[]> result = null;
		int[] array = null;
		if (characters.substring(i, i + 1).equals("{") && characters.indexOf("}", i) != -1) {
			int[] specials = getCode(characters.substring(i, characters.indexOf("}", i) + 1));
			if (specials != null) {
				array = specials;
				i = characters.indexOf("}", i);
			} else {
				array = getCode(characters.charAt(i) + "");
			}
		} else {
			array = getCode(characters.charAt(i) + "");
		}
		result = new Pair<Integer, int[]>(i, array);
		return result;
	}

	private void doType(int... keyCodes) {
		doType(keyCodes, 0, keyCodes.length);
	}

	private void doType(int[] keyCodes, int offset, int length) {
		if (length > 0) {
			ActionWait.waitMS(waitBetweenChar);
			try {
				Factory.get().getRobot().keyPress(keyCodes[offset]);
				doType(keyCodes, offset + 1, length - 1);
				Factory.get().getRobot().keyRelease(keyCodes[offset]);
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
	}

	public static int[] merge(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		int i = 0;
		for (; i < a.length; i++) {
			result[i] = a[i];
		}
		for (int j = 0; j < b.length; j++) {
			result[i + j] = b[j];
		}
		return result;
	}

}