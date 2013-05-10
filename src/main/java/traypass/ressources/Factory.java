package traypass.ressources;

import java.awt.Font;
import java.awt.Image;
import java.awt.Robot;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.TrayPass;
import traypass.misc.PassMenuItem;
import traypass.syntax.plugin.PluginManager;

public class Factory {

	private static final Logger logger = LoggerFactory.getLogger(Factory.class);

	public static final String updateUrl = "https://raw.github.com/jlaloi/TrayPass/master/";

	public static final String appName = "Tray Pass";

	public static String configFileName = ".TrayPass";

	public static String tmpDir = "TrayPass";

	public static String passFile = "c:\\TrayPass.txt";

	public static String iconFile = "DefaultIcon.png";

	public static String fontName = "Calibri";

	public static int fontSize = 11;

	public static Font font = null;

	public static Font fontInfo = null;

	public static Font fontBold = null;

	public static final String algorithm = "AES";

	public static String fileSeparator = System.getProperty("file.separator");

	public static String lineSeparator = System.getProperty("line.separator");

	public static TrayPassConfig trayConfig = new TrayPassConfig();

	public static SecretKey secretKey;

	public static Image trayImageIcon;

	public static String fileEncode = "ISO-8859-1";

	public static String consoleEncode = "CP850";

	public static int captureWidth = 1024;

	public static int imageCheckNumber = 60;

	public static int imageCheckInterval = 400;

	public static int iconSize = 16;

	public static String keyFile = "key_azerty.txt";

	public static TrayPass trayPass;

	private static Robot robot;

	private static PluginManager pluginManager;

	static {
		pluginManager = new PluginManager();
	}

	public static Robot getRobot() {
		if (robot == null) {
			try {
				robot = new Robot();
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
		return robot;
	}

	public static void compute() {
		passFile = trayConfig.getPassFile();
		iconFile = trayConfig.getIconFile();
		fileEncode = trayConfig.getFileEncode();
		consoleEncode = trayConfig.getConsoleEncode();
		fontName = trayConfig.getFont();
		fontSize = trayConfig.getFontSize();
		captureWidth = trayConfig.getCaptureWidth();
		imageCheckInterval = trayConfig.getImageCheckInterval();
		imageCheckNumber = trayConfig.getImageCheckNumber();
		iconSize = trayConfig.getIconSize();
		font = new Font(fontName, Font.PLAIN, fontSize);
		fontInfo = new Font(fontName, Font.PLAIN, fontSize + 1);
		fontBold = new Font(fontName, Font.BOLD, fontSize);
		keyFile = trayConfig.getKeyFile();
		PassMenuItem.defaultIcon = null;
		PassMenuItem.library.clear();
		pluginManager.initPluginList();
	}

	public static PluginManager getPluginManager() {
		return pluginManager;
	}

}
