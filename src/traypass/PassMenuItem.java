package traypass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import traypass.syntax.Interpreter;
import traypass.tools.ToolImage;

public class PassMenuItem extends JMenuItem {

	public static ImageIcon defaultIcon;

	public PassMenuItem(String label) {
		super(label);
		init(null);
		setIcon(defaultIcon);
	}

	public PassMenuItem(String label, String line) {
		super(label);
		init(line);
		setIcon(defaultIcon);
	}

	public PassMenuItem(String label, String line, String iconPath) {
		super(label);
		init(line);
		if (iconPath != null && iconPath.trim().length() > 0) {
			setIcon(getImageIcon(iconPath, this.getClass()));
		} else if (iconPath == null) {
			setIcon(defaultIcon);
		}
	}

	@SuppressWarnings("unchecked")
	public static ImageIcon getImageIcon(String path, Class c) {
		ImageIcon result = null;
		try {
			BufferedImage icon = ToolImage.resizeImage(ToolImage.getImage(path, c), TrayPassObject.iconSize, TrayPassObject.iconSize);
			result = new ImageIcon(icon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void init(final String line) {
		setFont(TrayPassObject.font);
		if (line != null && line.trim().length() > 0) {
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TrayPassObject.trayPass.setWorking(true);
					Interpreter.computeFunctions(line);
					TrayPassObject.trayPass.setWorking(false);
				}
			});
		}
		if (defaultIcon == null) {
			defaultIcon = getImageIcon("right.png", this.getClass());
		}
	}
}
