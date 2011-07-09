import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.crypto.SecretKey;
import javax.swing.UIManager;

public class TrayPass {

	public static String passFile = "c:\\TrayPass.txt";

	public static String imageFile = "Tatane.png";

	public static Image trayImageIcon;

	public static String title = "Tray Pass";

	public static String fontName = "Calibri";

	public static Font font = new Font(fontName, Font.PLAIN, 11);

	public static Font fontBold = new Font(fontName, Font.BOLD, 11);

	public static BufferedImage workingIcon;

	public static TrayIcon trayIcon;

	public static TrayConfig trayConfig;

	public static SecretKey key;

	class PassItem extends MenuItem {

		public PassItem(String label, final String line) {
			super(label);
			setFont(font);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					trayIcon.setImage(workingIcon);
					TrayAction.doAction(line);
					trayIcon.setImage(trayImageIcon);
					trayIcon.setToolTip(title);
				}
			});
		}

	}

	public TrayPass() {
		trayConfig = new TrayConfig();
		trayConfig.load();
		try {
			if (trayImageIcon == null) {
				trayImageIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(imageFile));
			}
			workingIcon = TrayTools.toBufferedImage(trayImageIcon);
			Graphics g = workingIcon.getGraphics();
			int rectSize = 6;
			g.setColor(Color.GREEN);
			g.fillOval(workingIcon.getWidth() - rectSize, workingIcon.getHeight() - rectSize, rectSize, rectSize);

			trayIcon = new TrayIcon(trayImageIcon, title);
			SystemTray tray = SystemTray.getSystemTray();
			setMenu();
			tray.add(trayIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMenu() {
		PopupMenu popup = new PopupMenu();
		boolean useEncryption = false;

		// Adding pass
		for (String pass : TrayTools.getFileLines(passFile)) {
			if (pass.contains("@encrypt")) {
				useEncryption = true;
			}
			if (pass.equals("line")) {
				popup.addSeparator();
			} else if (pass.startsWith("title:")) {
				MenuItem item = new MenuItem(pass.substring(pass.indexOf(":") + 1));
				item.setFont(fontBold);
				popup.add(item);
			} else {
				String label = pass;
				if (pass.startsWith("{")) {
					label = pass.substring(1, pass.indexOf("}"));
					pass = pass.substring(pass.indexOf("}") + 1);
				}
				MenuItem item = new PassItem(label, pass);
				popup.add(item);
			}
		}

		popup.addSeparator();

		// Adding Crypto items
		Menu crypto = new Menu("Encryption");
		MenuItem cryptoItem = new MenuItem("Config Encrypter");
		cryptoItem.setFont(font);
		cryptoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoConfigFrame();
			}
		});
		crypto.add(cryptoItem);
		MenuItem cryptoItem2 = new MenuItem("Set Encrypter");
		cryptoItem2.setFont(font);
		cryptoItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEnterFrame();
			}
		});
		crypto.add(cryptoItem2);
		MenuItem cryptoItem3 = new MenuItem("Encrypter help");
		cryptoItem3.setFont(font);
		cryptoItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEncryptFrame();
			}
		});
		crypto.add(cryptoItem3);
		crypto.setFont(font);
		popup.add(crypto);

		// Adding reload item
		MenuItem reloadItem = new MenuItem("Reload");
		reloadItem.setFont(font);
		reloadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenu();
			}
		});
		popup.add(reloadItem);

		// Adding exit item
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setFont(font);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trayConfig.save();
				System.exit(0);
			}
		});
		popup.add(exitItem);

		// Display
		trayIcon.setPopupMenu(popup);

		if (useEncryption) {
			new CryptoEnterFrame();
		}

	}

	public static void main(String[] args) {
		if (args.length > 0) {
			passFile = args[0];
		}

		if (args.length > 1) {
			imageFile = args[1];
			try {
				trayImageIcon = Toolkit.getDefaultToolkit().getImage(imageFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("passFile: " + passFile);
		System.out.println("imageFile: " + imageFile);

		if (SystemTray.isSupported()) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
			new TrayPass();
		}
	}
}
