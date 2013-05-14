package traypass.ressources;

import java.awt.Font;
import java.awt.Image;
import java.awt.Robot;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.TrayPass;
import traypass.configuration.TrayPassConfig;
import traypass.misc.PassMenuItem;
import traypass.syntax.plugin.PluginManager;
import traypass.tools.ToolImage;

public class Factory {

	private static final Logger logger = LoggerFactory.getLogger(Factory.class);

	public static final String updateUrl = "https://raw.github.com/jlaloi/TrayPass/master/";

	public static final String appName = "Tray Pass";

	public static final String tmpDir = "TrayPass";

	public static final String algorithm = "AES";

	public static final String fileSeparator = System.getProperty("file.separator");

	public static final String lineSeparator = System.getProperty("line.separator");

	public static final String defaultTrayConfigFile = "trayPass.properties";

	private static Factory instance;

	private TrayPassConfig trayConfig;

	private TrayPass trayPass;

	private Robot robot;

	private PluginManager pluginManager;

	private SecretKey secretKey;
	
	private Image trayImageIcon;

	public Factory(String TrayPassFile) {
		instance = this;
		try {
			robot = new Robot();
		} catch (Exception e) {
			logger.error("Error", e);
		}
		trayConfig = new TrayPassConfig(TrayPassFile);
		trayConfig.load();
		trayImageIcon = ToolImage.getImage(getConfig().getIconFile());
		pluginManager = new PluginManager();
		pluginManager.initPluginList();
		trayPass = new TrayPass();
		trayPass.setMenu();
	}

	public static Factory get() {
		return instance;
	}

	public Image getTrayImageIcon() {
		return trayImageIcon;
	}

	public void reset() {
		PassMenuItem.defaultIcon = null;
		PassMenuItem.library.clear();
		pluginManager.initPluginList();
	}

	public TrayPassConfig getConfig() {
		return trayConfig;
	}

	public TrayPass getTrayPass() {
		return trayPass;
	}

	public Font getFont() {
		return new Font(getConfig().getFontName(), Font.PLAIN, getConfig().getFontSize());
	}

	public Font getFontBold() {
		return new Font(getConfig().getFontName(), Font.BOLD, getConfig().getFontSize());
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public Robot getRobot() {
		return robot;
	}
}
