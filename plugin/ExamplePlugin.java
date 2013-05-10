import java.util.List;

import traypass.syntax.plugin.Plugin;

public class ExamplePlugin extends Plugin{
	
	public String getPattern() {
		return "pluginexample";
	}
	
	public String getDescription() {
		return "Plugin example";
	}

	public String[] getParams() {
		return new String[] { "<Parameter example>" };
	}


	public String doAction(List<String> parameters) {
		return "This is the plugin example: " + parameters.get(0);
	}
	
}
