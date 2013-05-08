package traypass.syntax.action;

import java.util.List;

import javax.swing.JOptionPane;

import traypass.syntax.Action;

public class ActionPrompt extends Action {

	public String doAction(List<String> parameters) {
		return (String) JOptionPane.showInputDialog(null, null, parameters.get(0), JOptionPane.PLAIN_MESSAGE, null, null, null);
	}

}