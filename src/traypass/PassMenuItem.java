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

import org.apache.log4j.Logger;

import traypass.log.LogFactory;
import traypass.tools.ToolImage;

public class PassMenuItem extends JMenuItem {
	
	private static final Logger logger = LogFactory.getLogger(PassMenuItem.class);

	public static ImageIcon defaultIcon;

	public static String defaultIconPath = "DefaultMenuIcon.png";

	public static HashMap<String, ImageIcon> library = new HashMap<String, ImageIcon>();
	
	public Object object;

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
			defaultIcon = getImageIcon(defaultIconPath, this.getClass());
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

	public static ImageIcon getImageIcon(String path, Class c) {
		ImageIcon result = null;
		try {
			if (path != null && library.containsKey(path)) {
				result = library.get(path);
			} else if (path != null && (defaultIconPath.equals(path) || new File(path).exists())) {
				Image image = ToolImage.getImage(path, c);
				BufferedImage icon = ToolImage.resizeImage(image, TrayPassObject.iconSize, TrayPassObject.iconSize);
				result = new ImageIcon(icon);
				library.put(path, result);
			} else {
				result = defaultIcon;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
