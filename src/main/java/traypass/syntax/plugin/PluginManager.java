package traypass.syntax.plugin;

import java.util.ArrayList;
import java.util.List;

public class PluginManager {

	public static final String pluginPath = "plugin";

	private List<Plugin> pluginList;

	private PluginLoader<Plugin> pluginManager;

	public PluginManager() {
		this.pluginList = new ArrayList<Plugin>();
		this.pluginManager = new PluginLoader<Plugin>(Plugin.class);
	}

	public void initPluginList() {
		pluginList.clear();
		pluginList.addAll(pluginManager.getClassFromPath(pluginPath, true));
	}

	public List<Plugin> getPluginList() {
		return pluginList;
	}

	public void resetPluginList() {
		pluginList.clear();
	}

}
