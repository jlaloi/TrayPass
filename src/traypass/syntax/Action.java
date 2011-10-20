package traypass.syntax;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {

	protected Interpreter interpreter;

	public String execute(Interpreter interpreter, List<String> parameters) {
		this.interpreter = interpreter;
		String result = "";
		List<String> computedParams = new ArrayList<String>();
		for (String param : parameters) {
			computedParams.add(executeParam(param));
		}
		if (!interpreter.isStop()) {
			result = doAction(computedParams);
		}
		return result;
	}

	protected String executeParam(String param) {
		String result = Interpreter.clearEscapeChar(interpreter.computeFunctions(param));
		return result;
	}

	public abstract String doAction(List<String> parameters);

}