package traypass.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import traypass.TrayPassObject;

public class Interpreter extends Thread {

	private String line;

	private boolean stop = false;

	public Interpreter(String line) {
		this.line = line;
	}

	public void run() {
		stop = false;
		TrayPassObject.trayPass.setWorking(true);
		computeFunctions(line);
		TrayPassObject.trayPass.setWorking(false);
		stop = true;
	}

	public String computeFunctions(String line) {
		String result = "";
		try {
			List<String> functions = splitFunctions(line);
			for (String function : functions) {
				if (stop) {
					if (function != null && function.trim().length() > 0) {
						TrayPassObject.trayPass.showError("Stopped before executing: " + function);
					}
					break;
				}
				result = computeFunction(function);
				if (result == null && !stop) {
					showError("Error while executing: " + function);
					break;
				}
			}
		} catch (Exception e) {
			showError("Exception while computeFunctions:" + line + ":\n" + e);
			e.printStackTrace();
		}
		return result;
	}

	public String computeFunction(String function) {
		String result = function;
		try {
			if (stop || !checkSyntax(function)) {
				return function;
			}
			int indexOfParam = function.indexOf(Syntax.functionParamStart);
			String methodName = function.substring(0, indexOfParam);
			String paramsS = function.substring(indexOfParam + 1);
			List<String> params = new ArrayList<String>();

			StringBuilder paramBuilder = new StringBuilder();
			int i = 0;
			int numberOfBracket = 1;
			while (numberOfBracket > 0 && i < paramsS.length()) {
				if (isSpecialChar(paramsS, i, Syntax.functionParamEnd)) {
					numberOfBracket--;
					if (numberOfBracket == 0) {
						String p = paramBuilder.toString();
						if (null != p && !"".equals(p)) {
							params.add(paramBuilder.toString());
						}
					} else {
						paramBuilder.append(paramsS.charAt(i));
					}
				} else if (isSpecialChar(paramsS, i, Syntax.functionStart)) {
					numberOfBracket++;
					paramBuilder.append(paramsS.charAt(i));
				} else if (isSpecialChar(paramsS, i, Syntax.functionParamSeparator) && numberOfBracket == 1) {
					params.add(paramBuilder.toString());
					paramBuilder = new StringBuilder();
				} else {
					paramBuilder.append(paramsS.charAt(i));
				}
				i++;
			}

			Action action = getAction(methodName, params.size());
			if (!stop && action != null) {
				System.out.println("Executing " + methodName);
				result = action.execute(this, params);
			}

		} catch (Exception e) {
			showError("Exception while executing:" + function + ":\n" + e);
			e.printStackTrace();
		}
		return result;
	}

	public static Action getAction(String functionName, int nbParameters) {
		Action result = null;
		for (Syntax syntax : Syntax.values()) {
			if (syntax.getPattern().toLowerCase().equals(functionName.toLowerCase()) && (syntax.getNbParameter() == -1 || syntax.getNbParameter() == nbParameters)) {
				result = syntax.getAction();
				break;
			}
		}
		return result;
	}

	public static boolean isSpecialChar(String str, int pos, char c) {
		boolean result = str.charAt(pos) == c;
		if (result) {
			int i = pos - 1;
			while (i >= 0 && str.charAt(i) == Syntax.escapeChar) {
				i--;
			}
			int mod = (pos - 1 - i) % 2;
			if (mod != 0) {
				result = false;
			}
		}
		return result;
	}

	public static boolean isSyntaxChar(char c) {
		boolean result = false;
		if (c == Syntax.functionParamEnd) {
			result = true;
		} else if (c == Syntax.functionParamStart) {
			result = true;
		} else if (c == Syntax.functionParamSeparator) {
			result = true;
		} else if (c == Syntax.functionSeparator) {
			result = true;
		} else if (c == Syntax.functionStart) {
			result = true;
		} else if (c == Syntax.escapeChar) {
			result = true;
		}
		return result;
	}

	private static boolean checkSyntax(String function) {
		Matcher matcher = Syntax.functionPattern.matcher(function);
		return matcher.find() && matcher.group().equals(function);
	}

	public static List<String> splitFunctions(String functions) {
		List<String> result = new ArrayList<String>();
		int lastPos = 0;
		for (int i = 0; i < functions.length(); i++) {
			if (isSpecialChar(functions, i, Syntax.functionSeparator)) {
				result.add(functions.substring(lastPos, i));
				lastPos = i + 1;
			}
		}
		result.add(functions.substring(lastPos, functions.length()));
		return result;
	}

	public static String clearEscapeChar(String str) {
		String result = "";
		for (int i = 0; str != null && i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == Syntax.escapeChar && i + 1 < str.length() && isSyntaxChar(str.charAt(i + 1))) {
				result += str.charAt(i + 1);
				i++;
			} else {
				result += c;
			}
		}
		return result;
	}

	public static void showError(String text) {
		if (TrayPassObject.trayPass != null) {
			TrayPassObject.trayPass.showError(text);
		} else {
			System.out.println("ERROR " + text);
		}
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

}