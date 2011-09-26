package traypass.syntax;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import traypass.syntax.action.ActionBrowse;
import traypass.syntax.action.ActionClipboard;
import traypass.syntax.action.ActionDate;
import traypass.syntax.action.ActionDecrypt;
import traypass.syntax.action.ActionDownload;
import traypass.syntax.action.ActionExecute;
import traypass.syntax.action.ActionExecuteResult;
import traypass.syntax.action.ActionFTP;
import traypass.syntax.action.ActionFile;
import traypass.syntax.action.ActionFileName;
import traypass.syntax.action.ActionFileSize;
import traypass.syntax.action.ActionInfo;
import traypass.syntax.action.ActionListDir;
import traypass.syntax.action.ActionMath;
import traypass.syntax.action.ActionMouse;
import traypass.syntax.action.ActionNewLine;
import traypass.syntax.action.ActionPack;
import traypass.syntax.action.ActionPrompt;
import traypass.syntax.action.ActionReadFile;
import traypass.syntax.action.ActionSave;
import traypass.syntax.action.ActionSelect;
import traypass.syntax.action.ActionSend;
import traypass.syntax.action.ActionStop;
import traypass.syntax.action.ActionWait;
import traypass.syntax.action.ActionWaitFor;
import traypass.syntax.action.logical.ActionAnd;
import traypass.syntax.action.logical.ActionEquals;
import traypass.syntax.action.logical.ActionForeach;
import traypass.syntax.action.logical.ActionFunction;
import traypass.syntax.action.logical.ActionIf;
import traypass.syntax.action.logical.ActionIs;
import traypass.syntax.action.logical.ActionNot;
import traypass.syntax.action.logical.ActionOr;
import traypass.syntax.action.logical.ActionSwitch;
import traypass.syntax.action.logical.ActionVar;
import traypass.syntax.action.logical.ActionWhile;
import traypass.syntax.action.str.ActionCaseLower;
import traypass.syntax.action.str.ActionCaseUpper;
import traypass.syntax.action.str.ActionConcat;
import traypass.syntax.action.str.ActionContains;
import traypass.syntax.action.str.ActionEndsWith;
import traypass.syntax.action.str.ActionIndexOf;
import traypass.syntax.action.str.ActionLastIndexOf;
import traypass.syntax.action.str.ActionLenght;
import traypass.syntax.action.str.ActionReplace;
import traypass.syntax.action.str.ActionStartsWith;
import traypass.syntax.action.str.ActionSub;
import traypass.syntax.action.str.ActionTrim;

public enum Syntax {

	WAIT(
			"wait",
			1,
			new ActionWait(),
			new String[] { "<time>" },
			"Wait specified millisecond"),

	PROMPT(
			"prompt",
			1,
			new ActionPrompt(),
			new String[] { "<label>" },
			"Display a prompt to enter a value"),

	EXECUTE(
			"execute",
			-1,
			new ActionExecute(),
			new String[] { "<executable>", "<Parameter>", "..." },
			"Execute the specified executable with specified parameter(s)"),

	EXECUTERESULT(
			"executeresult",
			-1,
			new ActionExecuteResult(),
			new String[] { "<executable>", "<Parameter>", "<...>" },
			"Return the execution result of the specified executable with specified parameter(s)"),

	SEND(
			"send",
			-1,
			new ActionSend(),
			new String[] { "<keys>" },
			"Simulate a keyboard to send specified keys"),

	READFILE(
			"readfile",
			1,
			new ActionReadFile(),
			new String[] { "<file path>" },
			"Read the content of the specified file"),

	FILE(
			"file",
			-1,
			new ActionFile(),
			new String[] { "<" + ActionFile.copy + "/" + ActionFile.delete + "/" + ActionFile.exist + "/" + ActionFile.move + ">", "<file path>", "<new file path>" },
			"Copy, move or delete a file"),

	DECRYPT(
			"decrypt",
			1,
			new ActionDecrypt(),
			new String[] { "<Encrypted text>" },
			"Decrypt the encrypted text"),

	CLIPBOARD(
			"clipboard",
			-1,
			new ActionClipboard(),
			new String[] { "<text>" },
			"Set the clipboard content with the specified text or without parameter to get the clipboard content"),

	SAVE(
			"save",
			3,
			new ActionSave(),
			new String[] { "<file path>", "<text>", "<bool append>" },
			"Add the specified text to the specified file"),

	PACK(
			"pack",
			-1,
			new ActionPack(),
			new String[] { "<file path>", "<param>" },
			"Execute the specified pack"),

	MOUSE(
			"mouse",
			3,
			new ActionMouse(),
			new String[] { "<x>", "<y>", "<click>" },
			"Click on the specified position"),

	FIND(
			"find",
			1,
			new ActionWaitFor(),
			new String[] { "<image path>" },
			"Waiting to find the image on the screen, return bool"),

	WAITFOR(
			"waitfor",
			2,
			new ActionWaitFor(),
			new String[] { "<image path>", "<click type>" },
			"Waiting to find the image on the screen and then perform the specified mouse click"),

	CONCAT(
			"concat",
			-1,
			new ActionConcat(),
			new String[] { "<text>", "<text>" },
			"Concatenate specified text"),

	DOWNLOAD(
			"download",
			2,
			new ActionDownload(),
			new String[] { "<url>", "<file>" },
			"Download specified url in the specified file"),

	REPLACE(
			"replace",
			3,
			new ActionReplace(),
			new String[] { "<in>", "<what>", "<by>" },
			"Replace in what by"),

	SELECT(
			"select",
			-1,
			new ActionSelect(),
			new String[] { "<message>", "<selected label>", "<label 1>", "<value 1>", "<label 2>", "<value 2>", "<...>" },
			"Return the value associated to the selected label"),

	LISTDIR(
			"listdir",
			1,
			new ActionListDir(),
			new String[] { "<path>" },
			"List all files in the selected path"),

	DATE(
			"date",
			0,
			new ActionDate(),
			new String[] {},
			"The current date"),

	VAR(
			"var",
			-1,
			new ActionVar(),
			new String[] { "<var name>", "<var value>" },
			"Set a var or get a var"),

	EQUALS(
			"equals",
			2,
			new ActionEquals(),
			new String[] { "<value>", "<value>" },
			"Compare two values, return bool"),

	IF(
			"if",
			-1,
			new ActionIf(),
			new String[] { "<bool>", "<then>", "<else>" },
			"If bool then else"),

	WHILE(
			"while",
			2,
			new ActionWhile(),
			new String[] { "<bool>", "<action>" },
			"While bool action"),

	INFO(
			"info",
			1,
			new ActionInfo(),
			new String[] { "<Text>" },
			"Display the text as info"),

	STOP(
			"stop",
			0,
			new ActionStop(),
			new String[] {},
			"Stop the execution"),

	NOT(
			"not",
			1,
			new ActionNot(),
			new String[] { "<bool>" },
			"Return bool inverse"),

	CONTAINS(
			"contains",
			2,
			new ActionContains(),
			new String[] { "<in>", "<what>" },
			"Return if what is in"),

	FOREACH(
			"foreach",
			-1,
			new ActionForeach(),
			new String[] { "<list>", "<var name>", "<action>", "<list separator (optional)>" },
			"Foreach all items of the list execute the action setting the current item list in the var"),

	FILENAME(
			"filename",
			1,
			new ActionFileName(),
			new String[] { "<file path>" },
			"Return the file name"),

	FILESIZE(
			"filesize",
			1,
			new ActionFileSize(),
			new String[] { "<file path>" },
			"Return the file size"),

	NEWLINE(
			"newline",
			0,
			new ActionNewLine(),
			new String[] {},
			"Return CR"),

	FTP(
			"ftp",
			7,
			new ActionFTP(),
			new String[] { "<host>", "<port>", "<user>", "<password>", "<" + ActionFTP.download + "/" + ActionFTP.upload + ">", "<Server File>", "Local File>" },
			"Return if the ftp transfert is ok"),

	BROWSE(
			"browse",
			2,
			new ActionBrowse(),
			new String[] { "<" + ActionBrowse.file + "/" + ActionBrowse.dir + ">", "<title>" },
			"Return selected file"),

	AND(
			"and",
			-1,
			new ActionAnd(),
			new String[] { "<bool>", "<bool>", "..." },
			"Logical AND"),

	MATH(
			"math",
			3,
			new ActionMath(),
			new String[] { "<operator>", "<double>", "<double>" },
			"Math operation (double)"),

	OR(
			"or",
			-1,
			new ActionOr(),
			new String[] { "<bool>", "<bool>", "..." },
			"Logical OR"),

	UPPERCASE(
			"uppercase",
			1,
			new ActionCaseUpper(),
			new String[] { "<String>" },
			"To upper case"),

	LOWERCASE(
			"lowercase",
			1,
			new ActionCaseLower(),
			new String[] { "<String>" },
			"To lower case"),

	SUBSTR(
			"substr",
			3,
			new ActionSub(),
			new String[] { "<String>,<from>,<to>" },
			"Substr"),

	INDEXOF(
			"indexof",
			-1,
			new ActionIndexOf(),
			new String[] { "<String>,<str>,<from*>" },
			"Index of"),

	LASTINDEXOF(
			"lastindexof",
			-1,
			new ActionLastIndexOf(),
			new String[] { "<String>,<str>,<from*>" },
			"Last index of"),

	LENGHT(
			"lenght",
			-1,
			new ActionLenght(),
			new String[] { "<String>" },
			"The string lenght"),

	SWITCH(
			"switch",
			-1,
			new ActionSwitch(),
			new String[] { "<value>", "<test 1>", "<result 1>", "<...>" },
			"Logical switch"),

	FUNCTION(
			"function",
			-1,
			new ActionFunction(),
			new String[] { "<function name>", "<function actions>" },
			"Declare a function or execute a function"),

	STARTSWITH(
			"startswith",
			-1,
			new ActionStartsWith(),
			new String[] { "<String>", "<Pattern>", "<Offset>" },
			"Starts with"),

	IS(
			"is",
			2,
			new ActionIs(),
			new String[] { "<" + ActionIs.file + "/" + ActionIs.directory + ">", "<File>" },
			"Is file/directory"),

	TRIM(
			"trim",
			1,
			new ActionTrim(),
			new String[] { "<String>" },
			"String trim"),

	ENDSSWITH(
			"endswith",
			-1,
			new ActionEndsWith(),
			new String[] { "<String>", "<Pattern>" },
			"Ends with")

	;

	public static final Pattern functionPattern = Pattern.compile("\\@([a-z])*\\((.*)\\)");

	public static final char functionStart = '@';

	public static final char functionParamStart = '(';

	public static final char functionParamEnd = ')';

	public static final char functionParamSeparator = ',';

	public static final char escapeChar = '\\';

	public static final String boolTrue = "true";

	public static final String boolFalse = "false";

	private String pattern;
	private int nbParameter;
	private Action action;
	private String[] params;
	private String description;

	Syntax(String pattern, int nbParameter, Action action, String[] params, String description) {
		this.pattern = Syntax.functionStart + pattern;
		this.action = action;
		this.nbParameter = nbParameter;
		this.description = description;
		this.params = params;
	}

	public String getPattern() {
		return pattern;
	}

	public int getNbParameter() {
		return nbParameter;
	}

	public Action getAction() {
		return action;
	}

	public String getExample() {
		String example = "";
		for (String param : params) {
			if (example.length() > 0) {
				example += Syntax.functionParamSeparator + param;
			} else {
				example += param;
			}
		}
		example = pattern + Syntax.functionParamStart + example + Syntax.functionParamEnd;
		return example;
	}

	public String[] getParams() {
		return params;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return name() + " : " + getDescription();
	}

	public static Collection<Syntax> getSort() {
		SortedMap<String, Syntax> map = new TreeMap<String, Syntax>();
		for (Syntax l : Syntax.values()) {
			map.put(l.getPattern(), l);
		}
		return map.values();
	}

}