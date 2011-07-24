package traypass.syntax.action;

import java.util.List;

import javax.swing.JOptionPane;

import traypass.TrayPass;
import traypass.syntax.Action;

public class ActionSelect extends Action {

	public String execute(List<String> parameters) {
		String title = parameters.get(0);
		parameters.remove(0);
		Object[] possibleValues = parameters.toArray();
		Object selectedValue = JOptionPane.showInputDialog(null, title, TrayPass.title, JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
		return (String) selectedValue;
	}

}