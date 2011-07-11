import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public enum TraySyntax {

	TITLE(
			"title:",
			"title:<Title text>",
			"Add a  title in the tray menu"),
	LINE(
			"line",
			"Add a new line in the tray menu"),
	NAME(
			"{<text>}<action>",
			"Set the label for the current tray line"),
	TEXT(
			"<text>",
			"Set the text to the clipboard"),
	SEPARATOR(
			"\\]\\[",
			"<action>][<action>",
			"Action separator"),
	WAIT(
			"@wait",
			"Wait 1 second"),
	BIGWAIT(
			"@bigwait",
			"Wait 5 seconds"),
	INPUT(
			"@input",
			"Display a prompt and wait for user input"),
	ENCRYPT(
			"@encrypt",
			"@encrypt{<Encrypted text>}",
			"Decrypt the encrypted text"),
	OPEN(
			"open:",
			"open:<executable path>#<argument>",
			"Execute the executable with specified argument(s)"),
	SEND(
			"send:",
			"send:<text>",
			"Simulate a keyboard and send the text"),
	FILE(
			"file:",
			"file:<file path>",
			"Set the content of the file in the clipboard"),
	PACK(
			"pack:",
			"pack:<pack path>#<argument>",
			"Execute the pack with specified argument(s)"),
	WAITFOR(
			"waitfor:",
			"waitfor:<Image file>",
			"Waiting to find the image in the screen"),
	WAITFORANDCLICK(
			"waitforandclick:",
			"waitforandclick:<Image file>",
			"Same than waitfor and then left click in the middle of the image"),
	WAITFORANDMIDDLECLICK(
			"waitforandmiddleclick:",
			"waitforandmiddleclick:<Image file>",
			"Same than waitfor and then middle click in the middle of the image"),
	WAITFORANDRIGHTCLICK(
			"waitforandrightclick:",
			"waitforandrightclick:<Image file>",
			"Same than waitfor and then right click in the middle of the image"),
	NOTE(
			"note:",
			"note:<text file>",
			"Save the clipboard content to the file");

	private final String pattern;
	private final String syntax;
	private final String description;

	TraySyntax(String pattern, String syntax, String description) {
		this.pattern = pattern;
		this.syntax = syntax;
		this.description = description;
	}

	TraySyntax(String pattern, String description) {
		this.pattern = pattern;
		this.syntax = pattern;
		this.description = description;
	}

	TraySyntax(String pattern) {
		this.pattern = pattern;
		this.syntax = pattern;
		this.description = pattern;
	}

	public String toString() {
		return pattern;
	}

	JLabel getLabel() {
		JLabel label = new JLabel(" " + syntax + " ==> " + description + " ");
		label.setFont(TrayObject.fontInfo);
		return label;
	}

	public String getPattern() {
		return pattern;
	}

	public String getSyntax() {
		return syntax;
	}

	public String getDescription() {
		return description;
	}

	public static void showSyntaxFrame() {
		JFrame frame = new JFrame("Syntax Description");
		frame.setBackground(Color.white);
		frame.setLayout(new GridLayout(TraySyntax.values().length + 1, 1));
		for (TraySyntax item : TraySyntax.values()) {
			frame.add(item.getLabel());
		}
		frame
				.add(new JLabel(
						" Example: {Mon Action}open:notepad][@bigwait][send:hello world!!{enter}][send:@encrypt{puDpHQjhpwyUBpJFKH3ZrQ==}{enter}@input][waitforandclick:C:\test.png "));
		frame.setIconImage(TrayObject.trayImageIcon);
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());
		frame.setVisible(true);
	}
}
