import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class WaitFor {

	private BufferedImage image;

	private boolean isFound = false;

	private int click = 0;

	public WaitFor(String path, int click) {
		this.click = click;
		try {
			File file = new File(path);
			if (file.exists()) {
				image = ImageIO.read(new File(path));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		isFound = false;
	}

	public boolean isOnDesktop() {
		if (!isFound && image != null) {
			try {
				Point result = isOnDesktop(image);
				isFound = result != null;
				if (click > 0 && result != null) {
					TrayObject.getRobot().mouseMove(result.x + (image.getWidth() / 2), result.y + (image.getHeight() / 2));
					TrayObject.getRobot().mousePress(click);
					TrayObject.getRobot().mouseRelease(click);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isFound;
	}

	public static Point isOnDesktop(BufferedImage image) {
		Point result = null;
		try {
			result = imageIncluded(TrayTools.getScreenCapture(), image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Point imageIncluded(BufferedImage desktop, BufferedImage pattern) {
		Point result = null;
		for (int y = 0; y <= desktop.getHeight() - pattern.getHeight(); y++) {
			for (int x = 0; x <= desktop.getWidth() - pattern.getWidth(); x++) {
				if (sameImage(desktop, pattern, x, y)) {
					System.out.println("Found at " + x + "x" + y);
					result = new Point(x, y);
					return result;
				}
			}
		}
		return result;
	}

	public static boolean sameImage(BufferedImage source, BufferedImage pattern, int startX, int startY) {
		if (source.getWidth() - startX < pattern.getWidth() || source.getHeight() - startY < pattern.getHeight()) {
			return false;
		}
		if (source.getRGB(startX, startY) != pattern.getRGB(0, 0)) {
			return false;
		}
		if (source.getRGB(startX + pattern.getWidth() - 1, startY + pattern.getHeight() - 1) != pattern.getRGB(
				pattern.getWidth() - 1, pattern.getHeight() - 1)) {
			return false;
		}
		if (source.getRGB(startX + pattern.getWidth() / 2 - 1, startY + pattern.getHeight() / 2 - 1) != pattern.getRGB(pattern
				.getWidth() / 2 - 1, pattern.getHeight() / 2 - 1)) {
			return false;
		}
		for (int y = 0; y < pattern.getHeight(); y++) {
			for (int x = 0; x < pattern.getWidth(); x++) {
				if (source.getRGB(x + startX, y + startY) != pattern.getRGB(x, y)) {
					return false;
				}
			}
		}
		return true;
	}
}