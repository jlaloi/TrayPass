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

import traypass.crypto.CryptoEnterFrame;
import traypass.frame.CaptureFrame;
import traypass.frame.ConfigFrame;
import traypass.frame.EditorFrame;
import traypass.frame.SyntaxFrame;
import traypass.misc.TrayUpdate;
import traypass.syntax.Interpreter;
import traypass.syntax.Syntax;
import traypass.tools.ToolFile;
import traypass.tools.ToolImage;

public class TrayPass {

	public static String title = "Tray Pass";

	public static BufferedImage workingIcon;

	public static TrayIcon trayIcon;

	private JPopupMenu popup;

	public static Interpreter interpreter;

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
					} else if (e.getClickCount() > 1) {
						stopCompute();
					} else {
						popup.setInvoker(popup);
						popup.setVisible(true);
						popup.setLocation(e.getX(), e.getY() - popup.getHeight());

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
		configMenu.setFont(TrayPassObject.fontBold);

		PassMenuItem editor = new PassMenuItem("Edit Menu");
		editor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditorFrame(TrayPassObject.passFile);
			}
		});
		configMenu.add(editor);
		PassMenuItem reloadItem = new PassMenuItem("Reload Menu");
		reloadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenu();
			}
		});
		configMenu.add(reloadItem);

		PassMenuItem configitem = new PassMenuItem("Configuration");
		configitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConfigFrame();
			}
		});
		configMenu.add(configitem);

		// Misc
		PassMenuItem helpItem = new PassMenuItem("Syntax help");
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SyntaxFrame();
			}
		});
		configMenu.add(helpItem);
		PassMenuItem updateItem = new PassMenuItem("Check for update");
		updateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TrayUpdate().manage();
			}
		});
		configMenu.add(updateItem);
		PassMenuItem exitItem = new PassMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		configMenu.add(exitItem);
		popup.add(configMenu);

		popup.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				if (e.getX() < popup.getLocation().x || e.getX() > popup.getSize().getWidth() || e.getY() < popup.getLocation().y || e.getY() > popup.getSize().getHeight()) {
					popup.setVisible(false);
				}
			}
		});

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

	public void showInfo(String text) {
		trayIcon.displayMessage(title, text, TrayIcon.MessageType.INFO);
	}

	public void compute(String line) {
		if (interpreter == null || interpreter.isStop()) {
			interpreter = new Interpreter(line);
			interpreter.start();
		} else {
			showError("You need to wait for/stop the current execution!\n" + interpreter.getLine());
		}
	}

	public void stopCompute() {
		if (interpreter != null && !interpreter.isStop()) {
			showInfo("Trying to stop the execution...");
			interpreter.setStop(true);
		}
	}

	private void exit() {
		TrayPassObject.trayConfig.save();
		System.exit(0);
	}
}