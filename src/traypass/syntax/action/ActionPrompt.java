package traypass.syntax.action;

import javax.swing.JOptionPane;

import traypass.syntax.Action;

public class ActionPrompt extends Action {

	public String execute(Object... parameter) {
		return (String) JOptionPane.showInputDialog(null, null, "Enter value", JOptionPane.PLAIN_MESSAGE, null, null, null);
	}

}