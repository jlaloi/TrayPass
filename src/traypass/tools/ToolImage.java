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
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

}