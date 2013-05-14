package traypass.syntax.plugin;

import traypass.syntax.Action;
import traypass.syntax.Function;

public abstract class Plugin extends Action implements Function {

	public String toString() {
		return getPattern().toUpperCase() + " : " + getDescription();
	}

	public String getPattern() {
		return Function.functionStart + getName();
	}

	public abstract String getName();

}
