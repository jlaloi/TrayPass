import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TrayTools {

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		image = new ImageIcon(image).getImage();

		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {

			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.BITMASK);
		} catch (HeadlessException e) {
			e.printStackTrace();
		}

		if (bimage == null) {
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		}

		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	public static List<String> getFileLines(String file) {
		List<String> result = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getFileContent(String file) {
		String result = "";
		for (String line : getFileLines(file)) {
			result += line + TrayObject.lineSeparator;
		}
		return result;
	}

	public static void execute(String[] cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static void setClipboard(String text) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
	}

	public static void waitMS(long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static String getInput() {
		return (String) JOptionPane.showInputDialog(null, null, "Enter value", JOptionPane.PLAIN_MESSAGE, null, null, null);
	}

}