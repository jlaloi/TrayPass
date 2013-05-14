package traypass.syntax;

import java.util.regex.Pattern;

public interface Function {

	public static final Pattern functionPattern = Pattern.compile("\\@([a-z])*\\((.*)\\)");

	public static final char functionStart = '@';

	public static final char functionParamStart = '(';

	public static final char functionParamEnd = ')';

	public static final char functionParamSeparator = ',';

	public static final char escapeChar = '\\';

	public static final String boolTrue = "true";

	public static final String boolFalse = "false";

	public String getPattern();

	public String getDescription();

	public String[] getParams();

}
