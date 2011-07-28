package traypass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import traypass.crypto.CryptoEncryptFrame;
import traypass.crypto.CryptoEnterFrame;
import traypass.frame.CaptureFrame;
import traypass.frame.ConfigFrame;
import traypass.frame.CreatorFrame;
import traypass.frame.SetEscapeFrame;
import traypass.syntax.Syntax;
import traypass.syntax.action.ActionExecute;
import traypass.tools.ToolFile;
import traypass.tools.ToolImage;

public class TrayPass {

	public static String title = "Tray Pass";

	public static BufferedImage workingIcon;

	public static TrayIcon trayIcon;

	private JPopupMenu popup;

	public void loadIcon() {
		TrayPassObject.trayImageIcon = ToolImage.getImage(TrayPassObject.iconFile, getClass());
		workingIcon = ToolImage.toBufferedImage(TrayPassObject.trayImageIcon);
		Graphics g = workingIcon.getGraphics();
		int rectSize = 6;
		g.setColor(Color.GREEN);
		g.fillOval(workingIcon.getWidth() - rectSize, workingIcon.getHeight() - rectSize, rectSize, rectSize);
		if (trayIcon != null) {
			trayIcon.setImage(TrayPassObject.trayImageIcon);
		}
	}

	public TrayPass() {
		try {
			loadIcon();
			trayIcon = new TrayIcon(TrayPassObject.trayImageIcon, title, null);
			trayIcon.setImageAutoSize(true);
			SystemTray tray = SystemTray.getSystemTray();
			setMenu();
			tray.add(trayIcon);
			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON2) {
						new CaptureFrame(ToolImage.getScreenCapture());
					} else {
						popup.setLocation(e.getX(), e.getY());
						popup.setInvoker(popup);
						popup.setVisible(true);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMenu() {
		popup = new JPopupMenu();
		boolean useEncryption = false;

		// Adding pass
		for (String pass : ToolFile.getFileLines(TrayPassObject.passFile)) {
			if (pass.contains(Syntax.DECRYPT.getPattern())) {
				useEncryption = true;
			}
			if (pass.contains(Syntax.DOWNLOAD.getPattern()) && TrayPassObject.trayConfig.getProxyPass() != null && TrayPassObject.trayConfig.getProxyPass().trim().length() > 0) {
				useEncryption = true;
			}
			if (pass.equals("line")) {
				popup.addSeparator();
			} else if (pass.startsWith("title:")) {
				PassMenuItem item = new PassMenuItem(pass.substring(pass.indexOf(":") + 1), null, "");
				item.setFont(TrayPassObject.fontBold);
				popup.add(item);
			} else {
				String label = pass;
				String icon = null;
				if (pass.startsWith("{")) {
					label = pass.substring(1, pass.indexOf("}"));
					pass = pass.substring(pass.indexOf("}") + 1);
					if (label.contains(Syntax.functionParamSeparator + "")) {
						String[] split = label.split(Syntax.functionParamSeparator + "");
						label = split[0];
						icon = split[1];
					}
				}
				PassMenuItem item = new PassMenuItem(label, pass, icon);
				popup.add(item);
			}
		}

		popup.addSeparator();
		JMenu configMenu = new JMenu("Configuration");
		configMenu.setFont(TrayPassObject.font);
		configMenu.setIcon(PassMenuItem.getImageIcon("config.png", this.getClass()));

		// Adding Crypto items
		PassMenuItem cryptoItem = new PassMenuItem("Config", null, "config.png");
		cryptoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConfigFrame();
			}
		});
		configMenu.add(cryptoItem);
		PassMenuItem cryptoItem3 = new PassMenuItem("Crypto Generate", null, "config.png");
		cryptoItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEncryptFrame();
			}
		});
		configMenu.add(cryptoItem3);
		PassMenuItem cryptoItem2 = new PassMenuItem("Crypto Set", null, "config.png");
		cryptoItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEnterFrame();
			}
		});
		configMenu.add(cryptoItem2);

		// Misc
		PassMenuItem editItem = new PassMenuItem("Edit Menu", null, "config.png");
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionExecute.execute(new String[] { "notepad", TrayPassObject.passFile });
			}
		});
		configMenu.add(editItem);
		PassMenuItem reloadItem = new PassMenuItem("Reload Menu", null, "config.png");
		reloadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenu();
			}
		});
		configMenu.add(reloadItem);
		PassMenuItem helpItem = new PassMenuItem("Syntax help", null, "help.png");
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Syntax.showSyntaxFrame();
			}
		});
		configMenu.add(helpItem);
		PassMenuItem creator = new PassMenuItem("Line creator", null, "help.png");
		creator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreatorFrame();
			}
		});
		configMenu.add(creator);
		PassMenuItem clearItem = new PassMenuItem("Escape help", null, "help.png");
		clearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SetEscapeFrame();
			}
		});
		configMenu.add(clearItem);

		popup.add(configMenu);

		// Adding exit item
		PassMenuItem exitItem = new PassMenuItem("Exit", null, "close.png");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		popup.add(exitItem);

		// Crypto
		if (useEncryption) {
			if (TrayPassObject.trayConfig.getCryptoExample() != null && TrayPassObject.trayConfig.getCryptoExample().trim().length() > 0) {
				new CryptoEnterFrame();
			} else if (TrayPassObject.secretKey == null) {
				new ConfigFrame();
			}
		}

	}

	public void setWorking(boolean bool) {
		if (bool) {
			trayIcon.setImage(workingIcon);
		} else {
			trayIcon.setImage(TrayPassObject.trayImageIcon);
			trayIcon.setToolTip(title);
		}
	}

	public void showError(String text) {
		trayIcon.displayMessage(title, text, TrayIcon.MessageType.ERROR);
	}

	private void exit() {
		TrayPassObject.trayConfig.save();
		System.exit(0);
	}
}