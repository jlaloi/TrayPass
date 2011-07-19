package traypass.syntax.action;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import traypass.TrayPass;
import traypass.syntax.Action;
import traypass.syntax.Interpreter;
import traypass.tools.ToolImage;
import traypass.tools.ToolMouse;

public class ActionWaitFor extends Action {

	private BufferedImage image;

	private String imagePath = "";

	private boolean isFound = false;

	private int click = 0;

	private final int maxCheck = 20;

	private final int checkWait = 500;

	public String execute(List<String> parameters) {
		this.click = Integer.valueOf(parameters.get(1));
		isFound = false;
		imagePath = parameters.get(0);
		try {
			File file = new File(imagePath);
			if (file.exists()) {
				image = ImageIO.read(file);
				for (int i = 0; i < maxCheck && !isOnDesktop(); i++) {
					TrayPass.trayIcon.setToolTip("Looking for " + imagePath + " (" + i + "/" + maxCheck + ")");
					ActionWait.waitMS(checkWait);
				}
			} else {
				Interpreter.showError("No image: " + imagePath);
			}
		} catch (Exception e) {
			Interpreter.showError("WaitFor: " + e);
			e.printStackTrace();
		}
		return "";
	}

	public boolean isOnDesktop() {
		if (!isFound && image != null) {
			Point result = isOnDesktop(image);
			isFound = result != null;
			if (click > 0 && result != null) {
				ToolMouse.doClick(result.x + (image.getWidth() / 2), result.y + (image.getHeight() / 2), click);
			}
		}
		return isFound;
	}

	public Point isOnDesktop(BufferedImage image) {
		Point result = null;
		result = imageIncluded(ToolImage.getScreenCapture(), image);
		return result;
	}

	public Point imageIncluded(BufferedImage desktop, BufferedImage pattern) {
		Point result = null;
		for (int y = 0; y <= desktop.getHeight() - pattern.getHeight(); y++) {
			for (int x = 0; x <= desktop.getWidth() - pattern.getWidth(); x++) {
				if (sameImage(desktop, pattern, x, y)) {
					System.out.println(imagePath + " found at " + x + "x" + y);
					result = new Point(x, y);
					return result;
				}
			}
		}
		return result;
	}

	public boolean sameImage(BufferedImage source, BufferedImage pattern, int startX, int startY) {
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