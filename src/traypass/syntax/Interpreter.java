package traypass.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.swing.JOptionPane;

public class Interpreter {

	private static boolean checkSyntax(String function) {
		Matcher matcher = Syntax.functionPattern.matcher(function);
		return matcher.find() && matcher.group().equals(function);
	}

	public static void computeFunctions(String line) {
		try {
			List<String> functions = splitFunctions(line);
			for (String function : functions) {
				computeFunction(function);
			}
		} catch (Exception e) {
			showError("Exception while computeFunctions:" + line + ":\n" + e);
			e.printStackTrace();
		}
	}

	public static String computeFunction(String function) {
		String result = function;
		try {
			if (!checkSyntax(function)) {
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
			if (action != null) {
				List<String> computedParams = new ArrayList<String>();
				for (String param : params) {
					computedParams.add(clearEscapeChar(computeFunction(param)));
				}
				System.out.println("Executing " + methodName);
				result = action.execute(computedParams);
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
			if (syntax.getPattern().equals(functionName)
					&& (syntax.getNbParameter() == -1 || syntax.getNbParameter() == nbParameters)) {
				result = syntax.getAction();
				break;
			}
		}
		return result;
	}

	public static boolean isSpecialChar(String str, int pos, char c) {
		boolean result = str.charAt(pos) == c;
		if (pos > 0 && result) {
			result = str.charAt(pos - 1) != Syntax.escapeChar;
		}
		return result;
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
		String result = str.replace(Syntax.escapeChar + "" + Syntax.functionParamSeparator, Syntax.functionParamSeparator + "");
		result = result.replace(Syntax.escapeChar + "" + Syntax.functionParamEnd, Syntax.functionParamEnd + "");
		result = result.replace(Syntax.escapeChar + "" + Syntax.functionParamStart, Syntax.functionParamStart + "");
		result = result.replace(Syntax.escapeChar + "" + Syntax.functionSeparator, Syntax.functionSeparator + "");
		result = result.replace(Syntax.escapeChar + "" + Syntax.functionStart, Syntax.functionStart + "");
		result = result.replace(Syntax.escapeChar + "" + Syntax.escapeChar, Syntax.escapeChar + "");
		return result;
	}

	public static void showError(String text) {
		JOptionPane.showMessageDialog(null, text, "TrayPass error!", JOptionPane.ERROR_MESSAGE);
	}

}