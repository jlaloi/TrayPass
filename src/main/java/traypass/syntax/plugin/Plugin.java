package traypass.syntax.plugin;

import traypass.syntax.Action;

public abstract class Plugin extends Action {
	
	public abstract String getPattern();
	
	public abstract String getDescription();
	
	public abstract String[] getParams();

}
