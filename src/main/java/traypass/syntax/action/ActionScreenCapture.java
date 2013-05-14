package traypass.syntax.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.ressources.Factory;
import traypass.syntax.Action;
import traypass.tools.ToolImage;

import com.sun.awt.AWTUtilities;

public class ActionScreenCapture extends Action {

	private static final Logger logger = LoggerFactory.getLogger(ActionScreenCapture.class);

	public static final String custom = "custom";

	public static final String full = "full";

	public static final String manual = "manual";

	public String doAction(List<String> parameters) {
		String imagePath = parameters.get(0);
		BufferedImage image = null;
		if (parameters.size() < 2 || full.equals(parameters.get(1).toLowerCase())) {
			image = ToolImage.getScreenCapture();
		} else if (manual.equals(parameters.get(1).toLowerCase())) {
			try {
				Font font = new Font(Factory.get().getConfig().getFontName(), Font.BOLD, 24);
				JDialog firstDialog = createWarningDialog("Select top left position", font);
				Thread.sleep(1500);
				Point firstLocation = MouseInfo.getPointerInfo().getLocation();
				firstDialog.dispose();
				JDialog firstConfirmDialog = createDialog("Set: x=" + firstLocation.x + ", y=" + firstLocation.y, font);
				Thread.sleep(1000);
				firstConfirmDialog.dispose();
				JDialog secondDialog = createWarningDialog("Select bottom right position", font);
				Thread.sleep(1500);
				Point secondLocation = MouseInfo.getPointerInfo().getLocation();
				secondDialog.dispose();
				JDialog secondConfirmDialog = createDialog("Set: x=" + +secondLocation.x + ", y=" + secondLocation.y, font);
				Thread.sleep(1000);
				secondConfirmDialog.dispose();
				int x = Math.min(firstLocation.x, secondLocation.x);
				int y = Math.min(firstLocation.y, secondLocation.y);
				int x2 = Math.max(firstLocation.x, secondLocation.x);
				int y2 = Math.max(firstLocation.y, secondLocation.y);
				Rectangle rectangle = new Rectangle(x, y, x2 - x, y2 - y);
				image = ToolImage.getScreenCapture(rectangle);
				JDialog confirmDialog = createDialog("Captured!!", font);
				Thread.sleep(1000);
				confirmDialog.dispose();
			} catch (Exception e) {
				logger.error("Error", e);
			}
		} else if (custom.equals(parameters.get(1).toLowerCase())) {
			int x = Integer.valueOf(parameters.get(2));
			int y = Integer.valueOf(parameters.get(3));
			int w = Integer.valueOf(parameters.get(4));
			int h = Integer.valueOf(parameters.get(5));
			Rectangle rectangle = new Rectangle(x, y, w, h);
			image = ToolImage.getScreenCapture(rectangle);
		}
		ToolImage.saveImage(image, new File(imagePath));
		return imagePath;
	}

	private JDialog createDialog(String text, Font font) {
		return createDialog(text, font, 300, 80, Color.black, Color.white);
	}

	private JDialog createWarningDialog(String text, Font font) {
		return createDialog(text, font, 300, 80, Color.black, Color.red);
	}

	private JDialog createDialog(String text, Font font, int width, int height, Color fontBackgroundColor, Color fontColor) {
		BufferedImage image = ToolImage.createImageWithText(text, font, 300, 80, fontBackgroundColor, fontColor);
		JDialog jDialog = new JDialog();
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		jDialog.add(label);
		jDialog.setFocusableWindowState(false);
		jDialog.setFocusable(false);
		jDialog.setUndecorated(true);
		jDialog.pack();
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		jDialog.setLocation(screenWidth - jDialog.getWidth(), screenHeight - jDialog.getHeight() - 50);
		jDialog.setAlwaysOnTop(true);
		AWTUtilities.setWindowOpaque(jDialog, false);
		jDialog.setVisible(true);
		return jDialog;
	}

}
