package traypass.syntax;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import traypass.TrayPassObject;
import traypass.syntax.action.ActionClipboard;
import traypass.syntax.action.ActionConcat;
import traypass.syntax.action.ActionEncrypt;
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
			"@wait{ms}",
			"Wait 1 second"),

	PROMPT(
			"@prompt",
			0,
			new ActionPrompt(),
			"@prompt",
			"Display a prompt to enter a value"),

	EXECUTE(
			"@execute",
			-1,
			new ActionExecute(),
			"@execute(<Parameter>,<Parameter>)",
			"Display a prompt to enter a value"),

	SEND(
			"@send",
			1,
			new ActionSend(),
			"@send{<String>}",
			"Simulate a keyboard and send specified string"),

	FILE(
			"@file",
			1,
			new ActionFile(),
			"@file{<file path>}",
			"Read the content of the specified file"),

	ENCRYPT(
			"@encrypt",
			1,
			new ActionEncrypt(),
			"@encrypt{<Encrypted text>}",
			"Decrypt the encrypted text"),

	CLIPBOARD(
			"@clipboard",
			-1,
			new ActionClipboard(),
			"@clipboard{<text>}",
			"Set the clipboard content with the specified text or without parameter get the clipboard content"),

	NOTE(
			"@note",
			2,
			new ActionNote(),
			"@note{<file path>,<text>}",
			"Add the specified text to the specified file"),

	PACK(
			"@pack",
			-1,
			new ActionPack(),
			"@pack{<file path>,<param>}",
			"Execute the specified pack"),

	MOUSE(
			"@mouse",
			3,
			new ActionMouse(),
			"@mouse{<x>,<y>,<click>}",
			"Click on the specified position"),

	WAITFOR(
			"@waitfor",
			2,
			new ActionWaitFor(),
			"@waitfor{<image path>,<click type>}",
			"Waiting to find the image in the screen and then perform the specified mouse"),

	CONCAT(
			"@concat",
			-1,
			new ActionConcat(),
			"@concat{<text>,<text>}",
			"Concatenate specified text");

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

	JLabel getLabel() {
		JLabel label = new JLabel(" " + example + " ==> " + description + " ");
		label.setFont(TrayPassObject.fontInfo);
		return label;
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

	public static void showSyntaxFrame() {
		JFrame frame = new JFrame("Syntax Description");
		frame.setBackground(Color.white);
		frame.setLayout(new GridLayout(Syntax.values().length, 1));
		for (Syntax item : Syntax.values()) {
			frame.add(item.getLabel());
		}
		frame.setIconImage(TrayPassObject.trayImageIcon);
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());
		frame.setVisible(true);
	}
}
