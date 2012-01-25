package traypass.syntax.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import traypass.syntax.Action;

public class ActionChrono extends Action {

	private static HashMap<String, Long> chrono = new HashMap<String, Long>();
	
	public static String start = "start";
	
	public static String stop = "stop";
	
	public static String pause = "pause";

	public String doAction(List<String> parameters) {
		String result = "";
		String action = parameters.get(0);
		String name = parameters.get(1);
		if(action.equals(start)){
			start(name);
		}else if(action.equals(stop)){
			result = stop(name) + "";
		}else if(action.equals(pause)){
			result = pause(name) + "";
		}
		return result;
	}

	public static void start(String name) {
		if (!chrono.containsKey(name)) {
			chrono.put(name, Calendar.getInstance().getTimeInMillis());
		} else {
			chrono.put(name, Calendar.getInstance().getTimeInMillis() - chrono.get(name));
		}
	}

	public static long stop(String name) {
		long result = 0;
		if (chrono.containsKey(name)) {
			result = Calendar.getInstance().getTimeInMillis() - chrono.get(name);
			chrono.remove(name);
		}
		return result;
	}

	public static long pause(String name) {
		long result = 0;
		if (chrono.containsKey(name)) {
			result = Calendar.getInstance().getTimeInMillis() - chrono.get(name);
			chrono.put(name, result);
		}
		return result;
	}

	public static void reset() {
		chrono.clear();
	}

	public static Set<String> getChronos() {
		return chrono.keySet();
	}
}
