package traypass;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import traypass.syntax.Interpreter;
import traypass.tools.ToolImage;

public class PassMenuItem extends JMenuItem {

	public static ImageIcon defaultIcon;

	public static HashMap<String, ImageIcon> library = new HashMap<String, ImageIcon>();

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

	private void init(final String line) {
		setFont(TrayPassObject.font);
		setMargin(new Insets(0, 0, 0, 0));
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

	@SuppressWarnings("unchecked")
	public static ImageIcon getImageIcon(String path, Class c) {
		ImageIcon result = null;
		if (library.containsKey(path)) {
			result = library.get(path);
		} else {
			try {
				Image image = ToolImage.getImage(path, c);
				BufferedImage icon = ToolImage.resizeImage(image, TrayPassObject.iconSize, TrayPassObject.iconSize);
				result = new ImageIcon(icon);
			} catch (Exception e) {
				e.printStackTrace();
			}
			library.put(path, result);
		}
		return result;
	}

}
