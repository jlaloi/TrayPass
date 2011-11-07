package traypass.syntax;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import traypass.syntax.action.ActionBrowse;
import traypass.syntax.action.ActionClipboard;
import traypass.syntax.action.ActionConfirm;
import traypass.syntax.action.ActionDate;
import traypass.syntax.action.ActionDecrypt;
import traypass.syntax.action.ActionDialog;
import traypass.syntax.action.ActionDownload;
import traypass.syntax.action.ActionExecute;
import traypass.syntax.action.ActionExecuteResult;
import traypass.syntax.action.ActionFTP;
import traypass.syntax.action.ActionFile;
import traypass.syntax.action.ActionFileName;
import traypass.syntax.action.ActionFileSize;
import traypass.syntax.action.ActionInfo;
import traypass.syntax.action.ActionJDBC;
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
import traypass.syntax.action.ActionSocket;
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
import traypass.syntax.action.str.ActionCount;
import traypass.syntax.action.str.ActionEndsWith;
import traypass.syntax.action.str.ActionIndexOf;
import traypass.syntax.action.str.ActionLastIndexOf;
import traypass.syntax.action.str.ActionLength;
import traypass.syntax.action.str.ActionReplace;
import traypass.syntax.action.str.ActionSplit;
import traypass.syntax.action.str.ActionStartsWith;
import traypass.syntax.action.str.ActionSub;
import traypass.syntax.action.str.ActionTrim;

public enum Syntax {

	WAIT(
			"wait",
			new ActionWait(),
			new String[] { "<time>" },
			"Wait specified millisecond"),

	PROMPT(
			"prompt",
			new ActionPrompt(),
			new String[] { "<label>" },
			"Display a prompt to enter a value"),

	EXECUTE(
			"execute",
			new ActionExecute(),
			new String[] { "<executable>", "<params>", "<Path>" },
			"Execute"),

	EXECUTERESULT(
			"executeresult",
			new ActionExecuteResult(),
			new String[] { "<executable>", "<params>", "<Path>" },
			"Return the execution result)"),

	SEND(
			"send",
			new ActionSend(),
			new String[] { "<keys>" },
			"Simulate a keyboard to send specified keys"),

	READFILE(
			"readfile",
			new ActionReadFile(),
			new String[] { "<file path>" },
			"Read the content of the specified file"),

	FILE(
			"file",
			new ActionFile(),
			new String[] { "<" + ActionFile.copy + "/" + ActionFile.delete + "/" + ActionFile.exist + "/" + ActionFile.move + ">", "<file path>", "<new file path>" },
			"Copy, move or delete a file"),

	DECRYPT(
			"decrypt",
			new ActionDecrypt(),
			new String[] { "<Encrypted text>" },
			"Decrypt the encrypted text"),

	CLIPBOARD(
			"clipboard",
			new ActionClipboard(),
			new String[] { "<text>" },
			"Set the clipboard content with the specified text or without parameter to get the clipboard content"),

	SAVE(
			"save",
			new ActionSave(),
			new String[] { "<file path>", "<text>", "<bool append>" },
			"Add the specified text to the specified file"),

	PACK(
			"pack",
			new ActionPack(),
			new String[] { "<file path>", "<param>" },
			"Execute the specified pack"),

	MOUSE(
			"mouse",
			new ActionMouse(),
			new String[] { "<x>", "<y>", "<click>" },
			"Click on the specified position"),

	FIND(
			"find",
			new ActionWaitFor(),
			new String[] { "<image path>" },
			"Waiting to find the image on the screen, return bool"),

	WAITFOR(
			"waitfor",
			new ActionWaitFor(),
			new String[] { "<image path>", "<click type>","<Max check>","<Check wait>" },
			"Waiting to find the image on the screen and then perform the specified mouse click"),

	CONCAT(
			"concat",
			new ActionConcat(),
			new String[] { "<text>", "<text>" },
			"Concatenate specified text"),

	DOWNLOAD(
			"download",
			new ActionDownload(),
			new String[] { "<url>", "<file>" },
			"Download specified url in the specified file"),

	REPLACE(
			"replace",
			new ActionReplace(),
			new String[] { "<in>", "<what>", "<by>" },
			"Replace in what by"),

	SELECT(
			"select",
			new ActionSelect(),
			new String[] { "<message>", "<selected label>", "<label 1>", "<value 1>", "<label 2>", "<value 2>", "<...>" },
			"Return the value associated to the selected label"),

	LISTDIR(
			"listdir",
			new ActionListDir(),
			new String[] { "<path>" },
			"List all files in the selected path"),

	DATE(
			"date",
			new ActionDate(),
			new String[] {},
			"The current date"),

	VAR(
			"var",
			new ActionVar(),
			new String[] { "<var name>", "<var value>" },
			"Set a var or get a var"),

	EQUALS(
			"equals",
			new ActionEquals(),
			new String[] { "<value>", "<value>" },
			"Compare two values, return bool"),

	IF(
			"if",
			new ActionIf(),
			new String[] { "<bool>", "<then>", "<else>" },
			"If bool then else"),

	WHILE(
			"while",
			new ActionWhile(),
			new String[] { "<bool>", "<action>" },
			"While bool action"),

	INFO(
			"info",
			new ActionInfo(),
			new String[] { "<Text>" },
			"Display the text as info"),

	DIALOG(
			"dialog",
			new ActionDialog(),
			new String[] { "<Text>", "<width>", "<height>", "<title>" },
			"Display the text in a dialog"),

	CONFIRM(
			"confirm",
			new ActionConfirm(),
			new String[] { "<title>", "<text>" },
			"Confirm dialog, return bool"),

	STOP(
			"stop",
			new ActionStop(),
			new String[] {},
			"Stop the execution"),

	NOT(
			"not",
			new ActionNot(),
			new String[] { "<bool>" },
			"Return bool inverse"),

	CONTAINS(
			"contains",
			new ActionContains(),
			new String[] { "<in>", "<what>" },
			"Return if what is in"),

	FOREACH(
			"foreach",
			new ActionForeach(),
			new String[] { "<list>", "<var name>", "<action>", "<list separator (optional)>" },
			"Foreach all items of the list execute the action setting the current item list in the var"),

	FILENAME(
			"filename",
			new ActionFileName(),
			new String[] { "<file path>" },
			"Return the file name"),

	FILESIZE(
			"filesize",
			new ActionFileSize(),
			new String[] { "<file path>" },
			"Return the file size"),

	NEWLINE(
			"newline",
			new ActionNewLine(),
			new String[] {},
			"Return CR"),

	FTP(
			"ftp",
			new ActionFTP(),
			new String[] { "<host>", "<port>", "<user>", "<password>", "<" + ActionFTP.download + "/" + ActionFTP.upload + ">", "<Server File>", "<Local File>" },
			"Return if the ftp transfert is ok"),

	BROWSE(
			"browse",
			new ActionBrowse(),
			new String[] { "<" + ActionBrowse.file + "/" + ActionBrowse.dir + ">", "<title>" },
			"Return selected file"),

	AND(
			"and",
			new ActionAnd(),
			new String[] { "<bool>", "<bool>", "..." },
			"Logical AND"),

	SOCKET(
			"socket",
			new ActionSocket(),
			new String[] { "<" + ActionSocket.server + "," + ActionSocket.client + ">", "<port>", "<File path/download directory>", "host" },
			"Socket file transfert"),

	MATH(
			"math",
			new ActionMath(),
			new String[] { "<operator>", "<double>", "<double>" },
			"Math operation (double)"),

	OR(
			"or",
			new ActionOr(),
			new String[] { "<bool>", "<bool>", "..." },
			"Logical OR"),

	UPPERCASE(
			"uppercase",
			new ActionCaseUpper(),
			new String[] { "<String>" },
			"To upper case"),

	LOWERCASE(
			"lowercase",
			new ActionCaseLower(),
			new String[] { "<String>" },
			"To lower case"),

	SUBSTR(
			"substr",
			new ActionSub(),
			new String[] { "<String>,<from>,<to>" },
			"Substr"),

	INDEXOF(
			"indexof",
			new ActionIndexOf(),
			new String[] { "<String>,<str>,<from*>" },
			"Index of"),

	LASTINDEXOF(
			"lastindexof",
			new ActionLastIndexOf(),
			new String[] { "<String>,<str>,<from*>" },
			"Last index of"),

	LENGTH(
			"length",
			new ActionLength(),
			new String[] { "<String>" },
			"The string lenght"),

	SWITCH(
			"switch",
			new ActionSwitch(),
			new String[] { "<value>", "<test 1>", "<result 1>", "<...>" },
			"Logical switch"),

	FUNCTION(
			"function",
			new ActionFunction(),
			new String[] { "<function name>", "<function actions>" },
			"Declare a function or execute a function"),

	STARTSWITH(
			"startswith",
			new ActionStartsWith(),
			new String[] { "<String>", "<Pattern>", "<Offset>" },
			"Starts with"),

	IS(
			"is",
			new ActionIs(),
			new String[] { "<" + ActionIs.file + "/" + ActionIs.directory + ">", "<File>" },
			"Is file/directory"),

	TRIM(
			"trim",
			new ActionTrim(),
			new String[] { "<String>" },
			"String trim"),

	ENDSSWITH(
			"endswith",
			new ActionEndsWith(),
			new String[] { "<String>", "<Pattern>" },
			"Ends with"),

	SPLIT(
			"split",
			new ActionSplit(),
			new String[] { "<String>", "<Separator>", "<Index>" },
			"String split"),

	COUNT(
			"count",
			new ActionCount(),
			new String[] { "<String>", "<String>" },
			"Sub string count"),

	JDBC(
			"jdbc",
			new ActionJDBC(),
			new String[] { "<driver>", "<url>", "<login>", "<password>", "<" + ActionJDBC.update + "/" + ActionJDBC.select + "/" + ActionJDBC.script + ">", "<query>", "<Separator>" },
			"JDBC");

	public static final Pattern functionPattern = Pattern.compile("\\@([a-z])*\\((.*)\\)");

	public static final char functionStart = '@';

	public static final char functionParamStart = '(';

	public static final char functionParamEnd = ')';

	public static final char functionParamSeparator = ',';

	public static final char escapeChar = '\\';

	public static final String boolTrue = "true";

	public static final String boolFalse = "false";

	private String pattern;
	private Action action;
	private String[] params;
	private String description;

	Syntax(String pattern, Action action, String[] params, String description) {
		this.pattern = Syntax.functionStart + pattern;
		this.action = action;
		this.description = description;
		this.params = params;
	}

	public String getPattern() {
		return pattern;
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