package traypass.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

	public static final Pattern pattern = Pattern.compile("\\@([a-z])*\\{(.*)\\}");

	public static final char functionStart = '@';

	public static final char functionParamStart = '{';

	public static final char functionParamEnd = '}';

	public static final String functionSeparator = "\\]\\[";

	private static boolean checkSyntax(String function) {
		Matcher matcher = pattern.matcher(function);
		return matcher.find() && matcher.group().equals(function);
	}

	public static void computeFunctions(String line) {
		String[] functions = line.split(functionSeparator);
		for (String function : functions) {
			computeFunction(function);
		}
	}

	public static String computeFunction(String function) {
		String result = function;
		try {
			if (!checkSyntax(function)) {
				return function;
			}
			int indexOfParam = function.indexOf(functionParamStart);
			String methodName = function.substring(0, indexOfParam);
			String paramsS = function.substring(indexOfParam + 1);
			List<String> params = new ArrayList<String>();

			StringBuilder paramBuilder = new StringBuilder();
			int i = 0;
			int numberOfBracket = 1;
			while (numberOfBracket > 0 && i < paramsS.length()) {
				char ch = paramsS.charAt(i);
				i++;
				if (ch == functionParamEnd) {
					numberOfBracket--;
					if (numberOfBracket == 0) {
						String p = paramBuilder.toString();
						if (null != p && !"".equals(p)) {
							params.add(paramBuilder.toString());
						}
					} else {
						paramBuilder.append(ch);
					}
				} else if (ch == functionStart) {
					numberOfBracket++;
					paramBuilder.append(ch);
				} else if (ch == ',' && numberOfBracket == 1) {
					params.add(paramBuilder.toString());
					paramBuilder = new StringBuilder();
				} else {
					paramBuilder.append(ch);
				}
			}

			List<String> computedParams = new ArrayList<String>();
			for (String param : params) {
				computedParams.add(computeFunction(param));
			}

			result = compute(methodName, computedParams);
		} catch (Exception e) {
			System.out.println("Exception while executing:" + function);
			e.printStackTrace();
		}
		return result;
	}

	public static String compute(String functionName, List<String> parameters) {
		String result = "";
		for (Syntax syntax : Syntax.values()) {
			if (syntax.getPattern().equals(functionName)
					&& (syntax.getNbParameter() == -1 || syntax.getNbParameter() == parameters.size())) {
				result = syntax.getAction().execute(parameters.toArray());
				break;
			}
		}
		return result;
	}
}
