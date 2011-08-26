package traypass.syntax.action.logical;

import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.syntax.Interpreter;

public class ActionForeach extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		String list = executeParam(parameters.get(0));
		String varName = executeParam(parameters.get(1));
		String action = parameters.get(2);
		String separatorName = TrayPassObject.lineSeparator;
		if (parameters.size() > 3) {
			separatorName = executeParam(parameters.get(3));
		}
		for (String item : list.split(separatorName)) {
			if (interpreter.isStop()) {
				break;
			}
			ActionVar.set(varName, executeParam(item));
			result = executeParam(action);
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}