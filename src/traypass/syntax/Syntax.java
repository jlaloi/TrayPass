package traypass.syntax;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;

import traypass.TrayPassObject;
import traypass.misc.TrayLabel;
import traypass.syntax.action.ActionClipboard;
import traypass.syntax.action.ActionConcat;
import traypass.syntax.action.ActionDecrypt;
import traypass.syntax.action.ActionDownload;
import traypass.syntax.action.ActionEquals;
import traypass.syntax.action.ActionExecute;
import traypass.syntax.action.ActionExecuteResult;
import traypass.syntax.action.ActionFile;
import traypass.syntax.action.ActionIf;
import traypass.syntax.action.ActionInfo;
import traypass.syntax.action.ActionListDir;
import traypass.syntax.action.ActionMouse;
import traypass.syntax.action.ActionNote;
import traypass.syntax.action.ActionPack;
import traypass.syntax.action.ActionPrompt;
import traypass.syntax.action.ActionReadFile;
import traypass.syntax.action.ActionReplace;
import traypass.syntax.action.ActionSelect;
import traypass.syntax.action.ActionSend;
import traypass.syntax.action.ActionVar;
import traypass.syntax.action.ActionWait;
import traypass.syntax.action.ActionWaitFor;
import traypass.syntax.action.ActionWhile;

public enum Syntax {

	WAIT(
			"@wait",
			1,
			new ActionWait(),
			"@wait(<time>)",
			"Wait specified millisecond"),

	PROMPT(
			"@prompt",
			1,
			new ActionPrompt(),
			"@prompt(<label>)",
			"Display a prompt to enter a value"),

	EXECUTE(
			"@execute",
			-1,
			new ActionExecute(),
			"@execute(<executable>,<Parameter>,<Parameter>)",
			"Execute the specified executable with specified parameter(s)"),

	EXECUTERESULT(
			"@executeresult",
			-1,
			new ActionExecuteResult(),
			"@executeresult(<executable>,<Parameter>,<Parameter>)",
			"Return the execution result of the specified executable with specified parameter(s)"),

	SEND(
			"@send",
			1,
			new ActionSend(),
			"@send(<keys>)",
			"Simulate a keyboard to send specified keys"),

	READFILE(
			"@readfile",
			1,
			new ActionReadFile(),
			"@readfile(<file path>)",
			"Read the content of the specified file"),

	FILE(
			"@file",
			-1,
			new ActionFile(),
			"@file(<copy/move/delete>,<file path>,<new file path>)",
			"Copy, move or delete a file"),

	DECRYPT(
			"@decrypt",
			1,
			new ActionDecrypt(),
			"@decrypt(<Encrypted text>)",
			"Decrypt the encrypted text"),

	CLIPBOARD(
			"@clipboard",
			-1,
			new ActionClipboard(),
			"@clipboard(<text>)",
			"Set the clipboard content with the specified text or without parameter to get the clipboard content"),

	NOTE(
			"@note",
			2,
			new ActionNote(),
			"@note(<file path>,<text>)",
			"Add the specified text to the specified file"),

	PACK(
			"@pack",
			-1,
			new ActionPack(),
			"@pack(<file path>,<param>)",
			"Execute the specified pack"),

	MOUSE(
			"@mouse",
			3,
			new ActionMouse(),
			"@mouse(<x>,<y>,<click>)",
			"Click on the specified position"),

	WAITFOR(
			"@waitfor",
			2,
			new ActionWaitFor(),
			"@waitfor(<image path>,<click type>)",
			"Waiting to find the image on the screen and then perform the specified mouse click"),

	CONCAT(
			"@concat",
			-1,
			new ActionConcat(),
			"@concat(<text>,<text>)",
			"Concatenate specified text"),

	DOWNLOAD(
			"@download",
			2,
			new ActionDownload(),
			"@download(<url>,<file>)",
			"Download specified url in the specified file"),

	REPLACE(
			"@replace",
			3,
			new ActionReplace(),
			"@replace(<in>,<what>,<by>)",
			"Replace in what by"),

	SELECT(
			"@select",
			-1,
			new ActionSelect(),
			"@select(<message>,<option 1>,<option2>,..)",
			"Return the selected option"),

	LISTDIR(
			"@listdir",
			1,
			new ActionListDir(),
			"@listdir(<path>)",
			"List all files in the selected path"),

	VAR(
			"@var",
			-1,
			new ActionVar(),
			"@var(<var name>,<var value>)",
			"Set a var or get a var"),

	EQUAL(
			"@equal",
			2,
			new ActionEquals(),
			"@equal(<value>,<value>)",
			"Compare two values, return bool"),

	IF(
			"@if",
			-1,
			new ActionIf(),
			"@if(<bool>,<then>,<else>)",
			"If bool then else"),

	WHILE(
			"@while",
			2,
			new ActionWhile(),
			"@while(<bool>,<action>)",
			"while bool action"),

	INFO(
			"@info",
			1,
			new ActionInfo(),
			"@info(<Text>)",
			"Display the text as info")

	;

	public static final Pattern functionPattern = Pattern.compile("\\@([a-z])*\\((.*)\\)");

	public static final char functionStart = '@';

	public static final char functionParamStart = '(';

	public static final char functionParamEnd = ')';

	public static final char functionSeparator = ';';

	public static final char functionParamSeparator = ',';

	public static final char escapeChar = '\\';

	public static final String boolTrue = "true";

	public static final String boolFalse = "false";

	private String pattern;
	private int nbParameter;
	private Action action;
	private String example;
	private String description;

	Syntax(String pattern, int nbParameter, Action action, String syntax, String description) {
		this.pattern = pattern;
		this.action = action;
		this.nbParameter = nbParameter;
		this.example = syntax;
		this.description = description;
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
		return example;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return name() + " : " + getDescription();
	}

	public static boolean isTrue(String bool) {
		boolean result = false;
		if (bool != null && bool.toLowerCase().equals(boolTrue)) {
			result = true;
		}
		return result;
	}

	public static void showSyntaxFrame() {
		JFrame frame = new JFrame("Syntax Description");
		frame.setBackground(Color.white);
		frame.setLayout(new GridLayout(Syntax.values().length + 4, 1));
		for (Syntax item : Syntax.values()) {
			JLabel label = new TrayLabel(" " + item.getExample() + " ==> " + item.getDescription() + " ");
			frame.add(label);
		}
		JLabel separator = new TrayLabel(" Function separator is " + functionSeparator);
		frame.add(separator);
		JLabel escape = new TrayLabel(" Escape character is " + escapeChar);
		frame.add(escape);
		JLabel bool = new TrayLabel(" Bools are  " + boolTrue + " and " + boolFalse);
		frame.add(bool);
		JLabel example = new TrayLabel(" Example: {example}@execute(notepad);@wait(400);@send(@concat(@prompt(text?),{enter}ok)) ");
		frame.add(example);
		frame.setIconImage(TrayPassObject.trayImageIcon);
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());
		frame.setVisible(true);
	}
}