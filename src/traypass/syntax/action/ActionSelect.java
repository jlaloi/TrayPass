package traypass.syntax.action;

import java.util.List;

import javax.swing.JOptionPane;

import traypass.TrayPass;
import traypass.syntax.Action;

public class ActionSelect extends Action {

	public String doAction(List<String> parameters) {
		String result = null;
		String title = parameters.get(0);
		Object def = parameters.get(1);
		parameters.remove(0);
		parameters.remove(0);
		Object[] possibleValues = new Object[parameters.size() / 2];
		for (int i = 0; i < possibleValues.length; i++) {
			possibleValues[i] = parameters.get(i * 2);
		}
		Object selectedValue = JOptionPane.showInputDialog(null, title, TrayPass.title, JOptionPane.INFORMATION_MESSAGE, null, possibleValues, def);
		result = parameters.get(parameters.indexOf(selectedValue) + 1);
		return result;
	}

}