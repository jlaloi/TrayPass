package traypass.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;
import traypass.syntax.plugin.Plugin;

public class Interpreter extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(Interpreter.class);

	private String line;

	private boolean stop = false;

	private boolean invisible = false;

	public Interpreter(String line) {
		this.line = line;
	}

	public void run() {
		stop = false;
		if (!invisible && Factory.get().getTrayPass() != null) {
			Factory.get().getTrayPass().setWorking(true);
		}
		computeFunctions(line);
		if (!invisible && Factory.get().getTrayPass() != null) {
			Factory.get().getTrayPass().setWorking(false);
		}
		stop = true;
	}

	public String computeFunctions(String line) {
		String result = "";
		try {
			for (String function : splitFunctions(line)) {
				if (stop) {
					if (function != null && function.trim().length() > 0) {
						Factory.get().getTrayPass().showError("Stopped before executing: " + function);
					}
					break;
				}
				if (function != null && function.length() > 0) {
					result = computeFunction(function);
				}
				if (result == null && !stop) {
					showError("Error while executing: " + function);
					break;
				}
			}
		} catch (Exception e) {
			showError("Exception while computeFunctions:" + line + ":\n" + e);
			logger.error("Error", e);
		}
		return result;
	}

	private String computeFunction(String function) {
		logger.debug("Executing " + function);
		String result = function;
		try {
			if (stop || !checkSyntax(function)) {
				return function;
			}
			int indexOfParam = function.indexOf(Function.functionParamStart);
			String methodName = function.substring(0, indexOfParam);
			String paramsS = function.substring(indexOfParam + 1);
			List<String> params = new ArrayList<String>();

			StringBuilder paramBuilder = new StringBuilder();
			int i = 0;
			int numberOfBracket = 1;
			while (numberOfBracket > 0 && i < paramsS.length()) {
				if (isSpecialChar(paramsS, i, Function.functionParamEnd)) {
					numberOfBracket--;
					if (numberOfBracket == 0) {
						String p = paramBuilder.toString();
						if (null != p && (!"".equals(p) || isEmptyParam(paramsS, i))) {
							params.add(paramBuilder.toString());
						}
					} else {
						paramBuilder.append(paramsS.charAt(i));
					}
				} else if (isSpecialChar(paramsS, i, Function.functionStart)) {
					numberOfBracket++;
					paramBuilder.append(paramsS.charAt(i));
				} else if (isSpecialChar(paramsS, i, Function.functionParamSeparator) && numberOfBracket == 1) {
					params.add(paramBuilder.toString());
					paramBuilder = new StringBuilder();
				} else {
					paramBuilder.append(paramsS.charAt(i));
				}
				i++;
			}

			Action action = getAction(methodName, params.size());
			if (!stop && action != null) {
				logger.info("Executing " + methodName);
				result = action.execute(this, params);
			}

		} catch (Exception e) {
			showError("Exception while executing:" + function + ":\n" + e);
			logger.error("Error", e);
		}
		return result;
	}

	public static Action getAction(String functionName, int nbParameters) {
		Action result = null;
		// Default action
		for (Syntax syntax : Syntax.values()) {
			if (syntax.getPattern().toLowerCase().equals(functionName.toLowerCase())) {
				result = syntax.getAction();
				break;
			}
		}
		// Plugin action
		if (result == null) {
			for (Plugin plugin : Factory.get().getPluginManager().getPluginList()) {
				if ((plugin.getPattern()).toLowerCase().equals(functionName.toLowerCase())) {
					result = plugin;
					break;
				}
			}
		}
		return result;
	}

	public static boolean isSpecialChar(String str, int pos, char c) {
		boolean result = str != null && pos >= 0 && pos < str.length() && str.charAt(pos) == c;
		if (result) {
			int i = pos - 1;
			while (i >= 0 && str.charAt(i) == Function.escapeChar) {
				i--;
			}
			int mod = (pos - 1 - i) % 2;
			if (mod != 0) {
				result = false;
			}
		}
		return result;
	}

	public static boolean isEmptyParam(String str, int pos) {
		boolean result = pos > 0 && (isSpecialChar(str, pos - 1, Function.functionParamSeparator)) || isSpecialChar(str, pos - 1, Function.functionParamStart);
		return result;
	}

	public static boolean isSyntaxChar(char c) {
		boolean result = false;
		if (c == Function.functionParamEnd) {
			result = true;
		} else if (c == Function.functionParamStart) {
			result = true;
		} else if (c == Function.functionParamSeparator) {
			result = true;
		} else if (c == Function.functionStart) {
			result = true;
		} else if (c == Function.escapeChar) {
			result = true;
		}
		return result;
	}

	private static boolean checkSyntax(String function) {
		Matcher matcher = Function.functionPattern.matcher(function);
		return matcher.find() && matcher.group().equals(function);
	}

	public static List<String> splitFunctions(String functions) {
		List<String> result = new ArrayList<String>();
		int nbIn = 0;
		int startPos = getNext(functions, 0, Function.functionStart);
		if (startPos > 0) {
			result.add(functions.substring(0, startPos));
		} else if (startPos == -1) {
			result.add(functions);
		}
		int pos = getNext(functions, startPos, Function.functionParamStart);
		while (pos < functions.length() && startPos > -1 && pos > 0) {
			if (isSpecialChar(functions, pos, Function.functionParamStart)) {
				nbIn++;
			} else if (isSpecialChar(functions, pos, Function.functionParamEnd)) {
				nbIn--;
			}
			if (nbIn == 0) {
				result.add(functions.substring(startPos, pos + 1));
				startPos = getNext(functions, pos, Function.functionStart);
				if (startPos > 0 && pos + 1 < functions.length() && pos + 1 != startPos) {
					result.add(functions.substring(pos + 1, startPos));
				} else if (startPos == -1 && pos + 1 != functions.length()) {
					result.add(functions.substring(pos + 1, functions.length()));
				}
				if (getNext(functions, startPos, Function.functionParamStart) == -1 && pos + 1 != functions.length()) {
					result.add(functions.substring(pos + 1, functions.length()));
				}
				pos = getNext(functions, startPos, Function.functionParamStart);
			} else {
				pos++;
			}
		}
		return result;
	}

	private static int getNext(String str, int start, char c) {
		int result = -1;
		for (int pos = start; str != null && pos < str.length(); pos++) {
			if (isSpecialChar(str, pos, c)) {
				result = pos;
				break;
			}
		}
		return result;
	}

	public static String clearEscapeChar(String str) {
		String result = "";
		for (int i = 0; str != null && i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == Function.escapeChar && i + 1 < str.length() && isSyntaxChar(str.charAt(i + 1))) {
				result += str.charAt(i + 1);
				i++;
			} else {
				result += c;
			}
		}
		return result;
	}

	public static void showError(String text) {
		if (Factory.get().getTrayPass() != null) {
			Factory.get().getTrayPass().showError(text);
		} else {
			System.out.println("ERROR " + text);
		}
	}

	public static boolean isTrue(String bool) {
		boolean result = false;
		if (bool != null && bool.trim().toLowerCase().equals(Function.boolTrue)) {
			result = true;
		}
		return result;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

}