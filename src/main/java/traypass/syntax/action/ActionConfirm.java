package traypass.syntax.action;

import java.util.List;

import javax.swing.JOptionPane;

import traypass.syntax.Action;
import traypass.syntax.Function;

public class ActionConfirm extends Action {

	public String doAction(List<String> parameters) {
		String result = Function.boolFalse;
		int test = JOptionPane.showConfirmDialog(null, parameters.get(1), parameters.get(0), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		System.out.println(test);
		if (test == 0) {
			result = Function.boolTrue;
		}
		return result;
	}

}