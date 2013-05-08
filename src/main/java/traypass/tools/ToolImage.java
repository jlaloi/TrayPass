package traypass.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;

public class ToolImage {

	private static final Logger logger = LoggerFactory.getLogger(ToolImage.class);

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
		BufferedImage desktop = Factory.getRobot().createScreenCapture(bounds);
		return desktop;
	}

	public static void saveImage(BufferedImage image, File file) {
		saveImage(image, file, "png");
	}

	public static void saveImage(BufferedImage image, File file, String ext) {
		try {
			ImageIO.write(image, ext, file);
		} catch (Exception e) {
			logger.error("Error", e);
		}
	}

	public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
		BufferedImage resutl = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = resutl.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return resutl;
	}

	public static BufferedImage resizeImage(Image image, int width, int height) {
		return resizeImage(toBufferedImage(image), width, height);
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		BufferedImage bimage = null;
		try {
			image = new ImageIcon(image).getImage();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			try {
				GraphicsDevice gs = ge.getDefaultScreenDevice();
				GraphicsConfiguration gc = gs.getDefaultConfiguration();
				bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.BITMASK);
			} catch (HeadlessException e) {
				logger.error("Error", e);
			}
			if (bimage == null) {
				bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			}
			Graphics g = bimage.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
		} catch (Exception e) {
			logger.error("Error", e);
		}
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

	public static Image getImage(String path) {
		Image result = null;
		try {
			if (isImageFile(path)) {
				if (!path.contains(Factory.fileSeparator)) {
					result = Toolkit.getDefaultToolkit().getImage(Factory.class.getResource(path));
				} else {
					result = Toolkit.getDefaultToolkit().getImage(path);
				}
			} else {
				result = iconToImage(getIconFile(path));
			}
		} catch (Exception e) {
			System.out.println("getImage exception " + path);
			logger.error("Error", e);
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

	public static Image resizeImage(Icon icon, int width, int height) {
		return resizeImage(iconToImage(icon), width, height);
	}

	public static BufferedImage loadImage(String path) {
		logger.info("Loading " + path);
		BufferedImage result = null;
		try {
			result = ImageIO.read(new File(path));
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return result;
	}

	public static BufferedImage createImageWithText(String text, Font font, int width, int height, Color fontBackgroundColor, Color fontColor) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics2D.setFont(font);

		FontMetrics fm = graphics2D.getFontMetrics(font);
		Rectangle2D rect = fm.getStringBounds(text, graphics2D);

		int x = (int) (bufferedImage.getWidth() - rect.getWidth() - 2);
		int y = bufferedImage.getHeight() - 2;

		graphics2D.setColor(fontBackgroundColor);
		graphics2D.drawString(text, x + 1, y + 1);

		graphics2D.setColor(fontColor);
		graphics2D.drawString(text, x, y);

		graphics2D.dispose();
		return bufferedImage;
	}

}