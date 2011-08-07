package traypass;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import traypass.tools.ToolImage;

public class PassMenuItem extends JMenuItem {

	public static ImageIcon defaultIcon;

	public static String defaultPath = "Default.png";

	public static HashMap<String, ImageIcon> library = new HashMap<String, ImageIcon>();

	public PassMenuItem(String label) {
		super(label);
		init(null);
		setIcon(defaultIcon);
	}

	public PassMenuItem(String label, Image icon) {
		super(label);
		init(null);
		setIcon(new ImageIcon(icon));
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
			Icon icon = getImageIcon(iconPath, this.getClass());
			if (icon != null) {
				setIcon(icon);
			}
		} else if (iconPath == null) {
			setIcon(defaultIcon);
		}
	}

	private void init(final String line) {
		if (defaultIcon == null) {
			defaultIcon = getImageIcon(defaultPath, this.getClass());
		}
		setFont(TrayPassObject.font);
		if (line != null && line.trim().length() > 0) {
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TrayPassObject.trayPass.compute(line);
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	public static ImageIcon getImageIcon(String path, Class c) {
		ImageIcon result = null;
		try {
			if (library.containsKey(path)) {
				result = library.get(path);
			} else if (defaultPath.equals(path) || new File(path).exists()) {
				Image image = ToolImage.getImage(path, c);
				BufferedImage icon = ToolImage.resizeImage(image, TrayPassObject.iconSize, TrayPassObject.iconSize);
				result = new ImageIcon(icon);
				library.put(path, result);
			} else {
				result = defaultIcon;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
