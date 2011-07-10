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
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TrayPass {

	public static String title = "Tray Pass";

	public static BufferedImage workingIcon;

	public static TrayIcon trayIcon;

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

			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						TrayObject.getRobot().mouseRelease(InputEvent.BUTTON3_MASK);
					} else if (e.getButton() == MouseEvent.BUTTON2) {
						new CaptureFrame(TrayTools.getScreenCapture());
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMenu() {
		TrayObject.trayConfig.load();
		PopupMenu popup = new PopupMenu();
		boolean useEncryption = false;

		// Adding pass
		for (String pass : TrayTools.getFileLines(TrayObject.passFile)) {
			if (pass.contains(TraySyntax.ENCRYPT.getPattern())) {
				useEncryption = true;
			}
			if (pass.equals(TraySyntax.LINE.getPattern())) {
				popup.addSeparator();
			} else if (pass.startsWith(TraySyntax.TITLE.getPattern())) {
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
		Menu configMenu = new Menu("Configuration");
		configMenu.setFont(TrayObject.font);

		// Adding Crypto items
		MenuItem cryptoItem = new MenuItem("Crypto Config");
		cryptoItem.setFont(TrayObject.font);
		cryptoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoConfigFrame();
			}
		});
		configMenu.add(cryptoItem);
		MenuItem cryptoItem3 = new MenuItem("Crypto Generate");
		cryptoItem3.setFont(TrayObject.font);
		cryptoItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEncryptFrame();
			}
		});
		configMenu.add(cryptoItem3);
		MenuItem cryptoItem2 = new MenuItem("Crypto Set");
		cryptoItem2.setFont(TrayObject.font);
		cryptoItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEnterFrame();
			}
		});
		configMenu.add(cryptoItem2);

		// Misc
		MenuItem editItem = new MenuItem("Edit Menu");
		editItem.setFont(TrayObject.font);
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrayTools.execute(new String[] { "notepad", TrayObject.passFile });
			}
		});
		configMenu.add(editItem);
		MenuItem reloadItem = new MenuItem("Reload Menu");
		reloadItem.setFont(TrayObject.font);
		reloadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenu();
			}
		});
		configMenu.add(reloadItem);
		MenuItem captureItem = new MenuItem("Screen Capture");
		captureItem.setFont(TrayObject.font);
		captureItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CaptureFrame(TrayTools.getScreenCapture());
			}
		});
		configMenu.add(captureItem);
		MenuItem helpItem = new MenuItem("Syntax help");
		helpItem.setFont(TrayObject.font);
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TraySyntax.showSyntaxFrame();
			}
		});
		configMenu.add(helpItem);

		popup.add(configMenu);

		// Adding exit item
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setFont(TrayObject.font);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		popup.add(exitItem);

		// Display
		trayIcon.setPopupMenu(popup);

		// Crypto
		if (useEncryption) {
			if (TrayObject.trayConfig.getCryptoExample() != null && TrayObject.trayConfig.getCryptoExample().trim().length() > 0) {
				new CryptoEnterFrame();
			} else if (TrayObject.secretKey == null) {
				new CryptoConfigFrame();
			}
		}

	}

	private void exit() {
		TrayObject.trayConfig.save();
		System.exit(0);
	}
}