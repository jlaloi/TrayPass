import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class TrayPass {

	public static String title = "Tray Pass";

	public static BufferedImage workingIcon;

	public static TrayIcon trayIcon;

	public static TrayConfig trayConfig;

	class PassItem extends MenuItem {

		public PassItem(String label, final String line) {
			super(label);
			setFont(TrayObject.font);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					trayIcon.setImage(workingIcon);
					TrayAction.doAction(line);
					trayIcon.setImage(TrayObject.trayImageIcon);
					trayIcon.setToolTip(title);
				}
			});
		}

	}

	public TrayPass() {
		trayConfig = new TrayConfig();
		trayConfig.load();
		try {
			if (TrayObject.trayImageIcon == null) {
				TrayObject.trayImageIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(TrayObject.iconFile));
			}
			workingIcon = TrayTools.toBufferedImage(TrayObject.trayImageIcon);
			Graphics g = workingIcon.getGraphics();
			int rectSize = 6;
			g.setColor(Color.GREEN);
			g.fillOval(workingIcon.getWidth() - rectSize, workingIcon.getHeight() - rectSize, rectSize, rectSize);

			trayIcon = new TrayIcon(TrayObject.trayImageIcon, title);
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
		for (String pass : TrayTools.getFileLines(TrayObject.passFile)) {
			if (pass.contains(TrayCMD.encrypt)) {
				useEncryption = true;
			}
			if (pass.equals(TrayCMD.line)) {
				popup.addSeparator();
			} else if (pass.startsWith(TrayCMD.title)) {
				MenuItem item = new MenuItem(pass.substring(pass.indexOf(":") + 1));
				item.setFont(TrayObject.fontBold);
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
		MenuItem cryptoItem = new MenuItem("Config");
		cryptoItem.setFont(TrayObject.font);
		cryptoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoConfigFrame();
			}
		});
		crypto.add(cryptoItem);
		MenuItem cryptoItem3 = new MenuItem("Generate");
		cryptoItem3.setFont(TrayObject.font);
		cryptoItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEncryptFrame();
			}
		});
		crypto.add(cryptoItem3);
		MenuItem cryptoItem2 = new MenuItem("Set");
		cryptoItem2.setFont(TrayObject.font);
		cryptoItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEnterFrame();
			}
		});
		crypto.add(cryptoItem2);
		crypto.setFont(TrayObject.font);
		popup.add(crypto);

		// Adding reload item
		MenuItem reloadItem = new MenuItem("Reload");
		reloadItem.setFont(TrayObject.font);
		reloadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenu();
			}
		});
		popup.add(reloadItem);

		// Adding exit item
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setFont(TrayObject.font);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trayConfig.save();
				System.exit(0);
			}
		});
		popup.add(exitItem);

		// Display
		trayIcon.setPopupMenu(popup);

		// Crypto
		if (useEncryption) {
			if (trayConfig.getCryptoExample() != null && trayConfig.getCryptoExample().trim().length() > 0) {
				new CryptoEnterFrame();
			} else if (TrayObject.secretKey == null) {
				new CryptoConfigFrame();
			}
		}

	}
}