package traypass.syntax;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;

import traypass.TrayPassObject;
import traypass.syntax.action.ActionClipboard;
import traypass.syntax.action.ActionConcat;
import traypass.syntax.action.ActionDecrypt;
import traypass.syntax.action.ActionExecute;
import traypass.syntax.action.ActionFile;
import traypass.syntax.action.ActionMouse;
import traypass.syntax.action.ActionNote;
import traypass.syntax.action.ActionPack;
import traypass.syntax.action.ActionPrompt;
import traypass.syntax.action.ActionSend;
import traypass.syntax.action.ActionWait;
import traypass.syntax.action.ActionWaitFor;

public enum Syntax {

	WAIT(
			"@wait",
			1,
			new ActionWait(),
			"@wait(ms)",
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

	SEND(
			"@send",
			1,
			new ActionSend(),
			"@send(<keys>)",
			"Simulate a keyboard to send specified keys"),

	FILE(
			"@file",
			1,
			new ActionFile(),
			"@file(<file path>)",
			"Read the content of the specified file"),

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
			"Concatenate specified text");

	public static final Pattern functionPattern = Pattern.compile("\\@([a-z])*\\((.*)\\)");

	public static final char functionStart = '@';

	public static final char functionParamStart = '(';

	public static final char functionParamEnd = ')';

	public static final char functionSeparator = ';';

	public static final char functionParamSeparator = ',';

	public static final char escapeChar = '\\';

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

	public static void showSyntaxFrame() {
		JFrame frame = new JFrame("Syntax Description");
		frame.setBackground(Color.white);
		frame.setLayout(new GridLayout(Syntax.values().length + 3, 1));
		for (Syntax item : Syntax.values()) {
			JLabel label = new JLabel(" " + item.getExample() + " ==> " + item.getDescription() + " ");
			label.setFont(TrayPassObject.fontInfo);
			frame.add(label);
		}
		JLabel separator = new JLabel(" Function separator is " + functionSeparator);
		separator.setFont(TrayPassObject.fontInfo);
		frame.add(separator);
		JLabel escape = new JLabel(" Escape character is " + escapeChar);
		escape.setFont(TrayPassObject.fontInfo);
		frame.add(escape);
		JLabel example = new JLabel(" Example: {example}@execute(notepad);@wait(400);@send(@concat(@prompt(text?),{enter}ok)) ");
		example.setFont(TrayPassObject.fontInfo);
		frame.add(example);
		frame.setIconImage(TrayPassObject.trayImageIcon);
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());
		frame.setVisible(true);
	}
}