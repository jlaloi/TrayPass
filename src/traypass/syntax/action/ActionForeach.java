package traypass.syntax.action;

import java.util.List;

import traypass.syntax.Action;
import traypass.syntax.Interpreter;

public class ActionForeach extends Action {

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		String list = parameters.get(0);
		String separatorName = parameters.get(1);
		String varName = parameters.get(2);
		String action = parameters.get(3);
		for (String item : list.split(separatorName)) {
			if (interpreter.isStop()) {
				break;
			}
			ActionVar.set(varName, executeParam(item));
			System.out.println("action " + action);
			result = executeParam(action);
			System.out.println("result " + result);
		}
		return result;
	}

	public String doAction(List<String> parameters) {
		return "";
	}
}