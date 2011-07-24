package traypass;

import java.awt.Font;
import java.awt.Image;
import java.awt.Robot;

import javax.crypto.SecretKey;

public class TrayPassObject {

	public static String configFileName = ".TrayPass";

	public static String tmpDir = "TrayPass";

	public static String passFile = "c:\\TrayPass.txt";

	public static String iconFile = "Tatane.png";

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

	public static String fileEncode = "UTF-8";

	public static String consoleEncode = "CP850";

	public static int captureWidth = 1024;

	public static TrayPass trayPass;

	private static Robot robot;

	public static Robot getRobot() {
		if (robot == null) {
			try {
				robot = new Robot();
			} catch (Exception e) {
				e.printStackTrace();
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
		font = new Font(fontName, Font.PLAIN, fontSize);
		fontInfo = new Font(fontName, Font.PLAIN, fontSize + 1);
		fontBold = new Font(fontName, Font.BOLD, fontSize);
	}
}