package traypass.tools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import traypass.TrayPassObject;

public class ToolImage {

	public static List<Rectangle> getScreenBounds() {
		List<Rectangle> result = new ArrayList<Rectangle>();
		for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
			result.add(device.getDefaultConfiguration().getBounds());
		}
		return result;
	}

	public static BufferedImage getScreenCapture() {
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;
		for (Rectangle b : getScreenBounds()) {
			width += b.width;
			height = Math.max(height, b.height);
			x = Math.min(x, b.x);
			y = Math.min(y, b.y);
		}
		return getScreenCapture(new Rectangle(x, y, width, height));
	}

	public static BufferedImage getScreenCapture(Rectangle bounds) {
		BufferedImage desktop = TrayPassObject.getRobot().createScreenCapture(bounds);
		return desktop;
	}

	public static void saveImage(BufferedImage image, File file) {
		try {
			ImageIO.write(image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return scaledImage;
	}

	public static BufferedImage resizeImage(Image image, int width, int height) {
		return resizeImage(toBufferedImage(image), width, height);
	}

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

	public static Image iconToImage(Icon icon) {
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		}
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(null, image.getGraphics(), 0, 0);
		return image;
	}

	@SuppressWarnings("unchecked")
	public static Image getImage(String path, Class c) {
		Image result = null;
		try {
			if (isImageFile(path)) {
				if (!path.contains(TrayPassObject.fileSeparator)) {
					result = Toolkit.getDefaultToolkit().getImage(c.getResource(path));
				} else {
					result = Toolkit.getDefaultToolkit().getImage(path);
				}
			} else {
				result = iconToImage(getIconFile(path));
			}
		} catch (Exception e) {
			System.out.println("getImage exception " + path);
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isImageFile(String path) {
		boolean result = false;
		if (path.toLowerCase().endsWith(".png")) {
			result = true;
		} else if (path.toLowerCase().endsWith(".jpg")) {
			result = true;
		} else if (path.toLowerCase().endsWith(".jpeg")) {
			result = true;
		} else if (path.toLowerCase().endsWith(".bmp")) {
			result = true;
		} else if (path.toLowerCase().endsWith(".ico")) {
			result = true;
		}
		return result;
	}

	public static Icon getIconFile(String path) {
		Icon result = null;
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			result = FileSystemView.getFileSystemView().getSystemIcon(file);
		}
		return result;
	}

}