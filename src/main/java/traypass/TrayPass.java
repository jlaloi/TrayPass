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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.crypto.CryptoEnterFrame;
import traypass.frame.CaptureFrame;
import traypass.frame.ConfigFrame;
import traypass.frame.EditorFrame;
import traypass.frame.SyntaxFrame;
import traypass.misc.TrayUpdate;
import traypass.ressources.Factory;
import traypass.syntax.Interpreter;
import traypass.syntax.Syntax;
import traypass.syntax.action.ActionSend;
import traypass.tools.ToolFile;
import traypass.tools.ToolImage;
import traypass.tools.ToolTimer;

public class TrayPass {

	private static final Logger logger = LoggerFactory.getLogger(TrayPass.class);

	public static String title = "Tray Pass";

	public static BufferedImage workingIcon;

	public static TrayIcon trayIcon;

	private JPopupMenu popup;

	public static Interpreter interpreter;

	public static List<ToolTimer> tasks = new ArrayList<ToolTimer>();

	public void loadIcon() {
		try {
			Factory.trayImageIcon = ToolImage.getImage(Factory.iconFile);
			workingIcon = ToolImage.toBufferedImage(Factory.trayImageIcon);
			Graphics g = workingIcon.getGraphics();
			int rectSize = 6;
			g.setColor(Color.GREEN);
			g.fillOval(workingIcon.getWidth() - rectSize, workingIcon.getHeight() - rectSize, rectSize, rectSize);
			if (trayIcon != null) {
				trayIcon.setImage(Factory.trayImageIcon);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while fetching the image icon " + Factory.trayImageIcon, e);
		}
	}

	public TrayPass() {
		try {
			loadIcon();
			trayIcon = new TrayIcon(Factory.trayImageIcon, title, null);
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
			logger.error("Error", e);
		}
	}

	public void setMenu() {

		// Reset timer
		for (ToolTimer t : tasks) {
			t.stop();
		}
		tasks.clear();

		popup = new JPopupMenu();
		boolean useEncryption = false;
		String toExecute = "";
		List<JMenu> currentMenu = new ArrayList<JMenu>();

		// Adding pass
		for (String pass : ToolFile.getFileLines(Factory.passFile)) {
			try {
				if (pass.contains(Syntax.DECRYPT.getPattern())) {
					useEncryption = true;
				}
				if (pass.contains(Syntax.DOWNLOAD.getPattern()) && Factory.trayConfig.getProxyPass() != null && Factory.trayConfig.getProxyPass().trim().length() > 0) {
					useEncryption = true;
				}
				if (pass.startsWith("<--{") && pass.endsWith("}")) {
					String label = pass.substring(4, pass.indexOf("}"));
					String icon = null;
					if (label.contains(Syntax.functionParamSeparator + "")) {
						icon = label.substring(label.indexOf(Syntax.functionParamSeparator) + 1);
						label = label.substring(0, label.indexOf(Syntax.functionParamSeparator));
					}
					JMenu menu = new JMenu(label);
					menu.setFont(Factory.font);
					menu.setIcon(PassMenuItem.getImageIcon(icon));
					if (currentMenu.size() > 0) {
						currentMenu.get(currentMenu.size() - 1).add(menu);
					} else {
						popup.add(menu);
					}
					currentMenu.add(menu);
				} else if (pass.equals("-->")) {
					if (currentMenu.size() > 0) {
						currentMenu.remove(currentMenu.size() - 1);
					}
				} else if (pass.startsWith("##")) {
					toExecute += pass.substring(2);
				} else if (pass.startsWith("task:{")) {
					String taskName = pass.substring(pass.indexOf("{") + 1, pass.indexOf("}"));
					String taskIcon = null;
					if (taskName.contains(Syntax.functionParamSeparator + "")) {
						taskIcon = taskName.substring(taskName.indexOf(Syntax.functionParamSeparator) + 1);
						taskName = taskName.substring(0, taskName.indexOf(Syntax.functionParamSeparator));
					}
					String taskTime = pass.substring(pass.indexOf("}") + 1, pass.indexOf(Syntax.functionParamSeparator, pass.indexOf("}")));
					String taskAction = pass.substring(pass.indexOf(Syntax.functionParamSeparator, pass.indexOf("}")) + 1);
					tasks.add(new ToolTimer(taskName, taskIcon, taskTime, taskAction));
				} else if (pass.equals("line")) {
					if (currentMenu.size() > 0) {
						currentMenu.get(currentMenu.size() - 1).addSeparator();
					} else {
						popup.addSeparator();
					}
				} else {
					PassMenuItem item;
					if (pass.startsWith("title:")) {
						item = new PassMenuItem(pass.substring(pass.indexOf(":") + 1), null, "");
						item.setFont(Factory.fontBold);
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
						item = new PassMenuItem(label, pass, icon);
					}
					if (currentMenu.size() > 0) {
						currentMenu.get(currentMenu.size() - 1).add(item);
					} else {
						popup.add(item);
					}
				}
			} catch (Exception e) {
				logger.error(pass + " " + e);
			}
		}

		popup.addSeparator();
		JMenu configMenu = new JMenu("Configuration");
		configMenu.setFont(Factory.fontBold);

		PassMenuItem editor = new PassMenuItem("Edit Menu");
		editor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditorFrame(Factory.passFile);
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

		if (tasks.size() > 0) {
			JMenu taskMenu = new JMenu("Tasks");
			taskMenu.setFont(Factory.fontBold);
			for (ToolTimer tt : tasks) {
				final PassMenuItem taskItem = new PassMenuItem(tt.getTitle());
				taskItem.setIcon(PassMenuItem.getImageIcon(tt.getIcon()));
				taskItem.setObject(tt);
				taskItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ToolTimer task = ((ToolTimer) taskItem.getObject());
						if (task.isStop()) {
							showInfo("Starting " + task.getTitle());
							task.start();
							taskItem.setText(task.getTitle());
						} else {
							showInfo("Stoping " + task.getTitle());
							task.stop();
							taskItem.setText(task.getTitle() + " (STOPPED)");
						}
					}
				});
				taskMenu.add(taskItem);
			}
			popup.add(taskMenu);
		}

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
			if (Factory.trayConfig.getCryptoExample() != null && Factory.trayConfig.getCryptoExample().trim().length() > 0) {
				new CryptoEnterFrame();
			} else if (Factory.secretKey == null) {
				new ConfigFrame();
			}
		}
		if (toExecute.trim().length() > 0) {
			compute(toExecute);
		}
		ActionSend.load();

		for (ToolTimer t : tasks) {
			t.start();
		}
	}

	public void setWorking(boolean bool) {
		if (trayIcon != null && bool) {
			trayIcon.setImage(workingIcon);
		} else if (trayIcon != null) {
			trayIcon.setImage(Factory.trayImageIcon);
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
		Factory.trayConfig.save();
		System.exit(0);
	}
}