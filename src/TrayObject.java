import java.awt.Font;
import java.awt.Image;

import javax.crypto.SecretKey;

public class TrayObject {

	public static String configFileName = ".TrayPass";

	public static String tmpDir = "TrayPass";

	public static String passFile = "c:\\TrayPass.txt";

	public static String iconFile = "Tatane.png";

	public static String fontName = "Calibri";

	public static Font font = new Font(fontName, Font.PLAIN, 11);

	public static Font fontBold = new Font(fontName, Font.BOLD, 11);

	public static final String algorithm = "AES";

	public static String fileSeparator = System.getProperty("file.separator");

	public static String lineSeparator = System.getProperty("line.separator");

	public static SecretKey secretKey;

	public static Image trayImageIcon;

}
