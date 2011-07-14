package traypass;

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

import traypass.crypto.CryptoConfigFrame;
import traypass.crypto.CryptoEncryptFrame;
import traypass.crypto.CryptoEnterFrame;
import traypass.misc.CaptureFrame;
import traypass.syntax.Interpreter;
import traypass.syntax.Syntax;
import traypass.syntax.action.ActionExecute;
import traypass.tools.ToolFile;
import traypass.tools.ToolImage;

public class TrayPass {

	public static String title = "Tray Pass";

	public static BufferedImage workingIcon;

	public static TrayIcon trayIcon;

	class PassItem extends MenuItem {
		public PassItem(String label, final String line) {
			super(label);
			setFont(TrayPassObject.font);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					trayIcon.setImage(workingIcon);
					Interpreter.computeFunctions(line);
					trayIcon.setImage(TrayPassObject.trayImageIcon);
					trayIcon.setToolTip(title);
				}
			});
		}
	}

	public TrayPass() {
		try {
			if (TrayPassObject.trayImageIcon == null) {
				TrayPassObject.trayImageIcon = Toolkit.getDefaultToolkit().getImage(
						getClass().getResource(TrayPassObject.iconFile));
			}
			workingIcon = ToolImage.toBufferedImage(TrayPassObject.trayImageIcon);
			Graphics g = workingIcon.getGraphics();
			int rectSize = 6;
			g.setColor(Color.GREEN);
			g.fillOval(workingIcon.getWidth() - rectSize, workingIcon.getHeight() - rectSize, rectSize, rectSize);

			trayIcon = new TrayIcon(TrayPassObject.trayImageIcon, title);
			SystemTray tray = SystemTray.getSystemTray();
			setMenu();
			tray.add(trayIcon);

			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						TrayPassObject.getRobot().mouseRelease(InputEvent.BUTTON3_MASK);
					} else if (e.getButton() == MouseEvent.BUTTON2) {
						new CaptureFrame(ToolImage.getScreenCapture());
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMenu() {
		TrayPassObject.trayConfig.load();
		PopupMenu popup = new PopupMenu();
		boolean useEncryption = false;

		// Adding pass
		for (String pass : ToolFile.getFileLines(TrayPassObject.passFile)) {
			if (pass.contains(Syntax.DECRYPT.getPattern())) {
				useEncryption = true;
			}
			if (pass.equals("line")) {
				popup.addSeparator();
			} else if (pass.startsWith("title:")) {
				MenuItem item = new MenuItem(pass.substring(pass.indexOf(":") + 1));
				item.setFont(TrayPassObject.fontBold);
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
		configMenu.setFont(TrayPassObject.font);

		// Adding Crypto items
		MenuItem cryptoItem = new MenuItem("Crypto Config");
		cryptoItem.setFont(TrayPassObject.font);
		cryptoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoConfigFrame();
			}
		});
		configMenu.add(cryptoItem);
		MenuItem cryptoItem3 = new MenuItem("Crypto Generate");
		cryptoItem3.setFont(TrayPassObject.font);
		cryptoItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEncryptFrame();
			}
		});
		configMenu.add(cryptoItem3);
		MenuItem cryptoItem2 = new MenuItem("Crypto Set");
		cryptoItem2.setFont(TrayPassObject.font);
		cryptoItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEnterFrame();
			}
		});
		configMenu.add(cryptoItem2);

		// Misc
		MenuItem editItem = new MenuItem("Edit Menu");
		editItem.setFont(TrayPassObject.font);
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionExecute.execute(new String[] { "notepad", TrayPassObject.passFile });
			}
		});
		configMenu.add(editItem);
		MenuItem reloadItem = new MenuItem("Reload Menu");
		reloadItem.setFont(TrayPassObject.font);
		reloadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenu();
			}
		});
		configMenu.add(reloadItem);
		MenuItem captureItem = new MenuItem("Screen Capture");
		captureItem.setFont(TrayPassObject.font);
		captureItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CaptureFrame(ToolImage.getScreenCapture());
			}
		});
		configMenu.add(captureItem);
		MenuItem helpItem = new MenuItem("Syntax help");
		helpItem.setFont(TrayPassObject.font);
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Syntax.showSyntaxFrame();
			}
		});
		configMenu.add(helpItem);

		popup.add(configMenu);

		// Adding exit item
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setFont(TrayPassObject.font);
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
			if (TrayPassObject.trayConfig.getCryptoExample() != null
					&& TrayPassObject.trayConfig.getCryptoExample().trim().length() > 0) {
				new CryptoEnterFrame();
			} else if (TrayPassObject.secretKey == null) {
				new CryptoConfigFrame();
			}
		}

	}

	private void exit() {
		TrayPassObject.trayConfig.save();
		System.exit(0);
	}
}