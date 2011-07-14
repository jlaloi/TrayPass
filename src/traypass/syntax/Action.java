package traypass.syntax;

public abstract class Action {

	public abstract String execute(Object... parameter);

	public String[] getStringArray(Object[] parameter) {
		String[] result = new String[parameter.length];
		for (int i = 0; i < parameter.length; i++) {
			result[i] = (String) parameter[i];
		}
		return result;
	}
}