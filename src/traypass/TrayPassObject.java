package traypass;

import java.awt.AWTException;
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

	public static Font font = new Font(fontName, Font.PLAIN, 11);

	public static Font fontInfo = new Font(fontName, Font.PLAIN, 12);

	public static Font fontBold = new Font(fontName, Font.BOLD, 11);

	public static final String algorithm = "AES";

	public static String fileSeparator = System.getProperty("file.separator");

	public static String lineSeparator = System.getProperty("line.separator");

	public static TrayPassConfig trayConfig = new TrayPassConfig();

	public static SecretKey secretKey;

	public static Image trayImageIcon;

	private static Robot robot;

	public static Robot getRobot() {
		if (robot == null) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		return robot;
	}
}