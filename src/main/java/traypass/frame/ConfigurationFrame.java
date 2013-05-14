package traypass.frame;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.crypto.CryptoEncrypter;
import traypass.misc.TrayButton;
import traypass.misc.TrayLabel;
import traypass.misc.TrayTextField;
import traypass.ressources.Factory;
import traypass.syntax.Interpreter;

public class ConfigurationFrame extends JDialog {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationFrame.class);

	private JTextField cryptoKey, cryptoExample, proxyHost, proxyPort, proxyUser, font, fontSize, captureWidth, fileEncode, consoleEncode, passFile, iconFile, imageCheckNumber, imageCheckInterval, iconSize, keyFile;
	private JPasswordField proxyPass;
	private JButton save;

	public ConfigurationFrame() {
		cryptoKey = new TrayTextField();
		cryptoExample = new TrayTextField();
		proxyHost = new TrayTextField(Factory.get().getConfig().getProxyHost());
		proxyPort = new TrayTextField(Factory.get().getConfig().getProxyPort() + "");
		proxyUser = new TrayTextField(Factory.get().getConfig().getProxyUser());
		proxyPass = new JPasswordField();
		if (Factory.get().getConfig().getProxyPass() != null && Factory.get().getConfig().getProxyPass().trim().length() > 0) {
			if (Factory.get().getSecretKey() != null) {
				proxyPass.setText(CryptoEncrypter.decrypt(Factory.get().getConfig().getProxyPass(), Factory.get().getSecretKey()));
			} else {
				Interpreter.showError("You need to set your encryption key to save the proxy password.");
				return;
			}
		}
		font = new TrayTextField(Factory.get().getConfig().getFontName());
		fontSize = new TrayTextField(Factory.get().getConfig().getFontSize() + "");
		captureWidth = new TrayTextField(Factory.get().getConfig().getCaptureWidth() + "");
		fileEncode = new TrayTextField(Factory.get().getConfig().getFileEncode());
		consoleEncode = new TrayTextField(Factory.get().getConfig().getConsoleEncode());
		passFile = new TrayTextField(Factory.get().getConfig().getMenuFile());
		iconFile = new TrayTextField(Factory.get().getConfig().getIconFile());
		imageCheckNumber = new TrayTextField(Factory.get().getConfig().getImageCheckNumber() + "");
		imageCheckInterval = new TrayTextField(Factory.get().getConfig().getImageCheckInterval() + "");
		iconSize = new TrayTextField(Factory.get().getConfig().getIconSize() + "");
		keyFile = new TrayTextField(Factory.get().getConfig().getKeyFile());

		save = new TrayButton("Save");

		setLayout(new GridLayout(18, 2));

		add(new TrayLabel(" Pass file:"));
		add(passFile);

		add(new TrayLabel(" Icon:"));
		add(iconFile);

		add(new TrayLabel(" Your key:"));
		add(cryptoKey);

		add(new TrayLabel(" Your test sentence:"));
		add(cryptoExample);

		add(new TrayLabel(" Font Name:"));
		add(font);

		add(new TrayLabel(" Font Size:"));
		add(fontSize);

		add(new TrayLabel(" Icon Size:"));
		add(iconSize);

		add(new TrayLabel(" Capture window width:"));
		add(captureWidth);

		add(new TrayLabel(" File Encoding:"));
		add(fileEncode);

		add(new TrayLabel(" Console Encoding:"));
		add(consoleEncode);

		add(new TrayLabel(" Key File:"));
		add(keyFile);

		add(new TrayLabel(" Number of image checking:"));
		add(imageCheckNumber);

		add(new TrayLabel(" Interval between image checking (ms):"));
		add(imageCheckInterval);

		add(new TrayLabel(" Proxy Host:"));
		add(proxyHost);

		add(new TrayLabel(" Proxy Port:"));
		add(proxyPort);

		add(new TrayLabel(" Proxy User:"));
		add(proxyUser);

		add(new TrayLabel(" Proxy Pass:"));
		add(proxyPass);

		add(new TrayLabel(""));
		add(save);

		pack();
		setTitle("Tray Encrypter Config");
		setIconImage(Factory.get().getTrayImageIcon());
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);

		proxyHost.requestFocus();

		iconFile.addMouseListener(new FileAdapater(iconFile));
		passFile.addMouseListener(new FileAdapater(passFile));

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				if (cryptoKey.getText().trim().length() > 0) {
					Factory.get().setSecretKey(CryptoEncrypter.getSecretKey(cryptoKey.getText()));
					Factory.get().getConfig().setCryptoExample(CryptoEncrypter.encrypt(cryptoExample.getText(), Factory.get().getSecretKey()));
				}
				Factory.get().getConfig().setProxyHost(proxyHost.getText());
				Factory.get().getConfig().setProxyUser(proxyUser.getText());
				Factory.get().getConfig().setFontName(font.getText());
				Factory.get().getConfig().setFileEncode(fileEncode.getText());
				Factory.get().getConfig().setConsoleEncode(consoleEncode.getText());
				Factory.get().getConfig().setMenuFile(passFile.getText());
				Factory.get().getConfig().setIconFile(iconFile.getText());
				Factory.get().getConfig().setKeyFile(keyFile.getText());
				try {
					Factory.get().getConfig().setProxyPort(Integer.valueOf(proxyPort.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.get().getConfig().setCaptureWidth(Integer.valueOf(captureWidth.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.get().getConfig().setFontSize(Integer.valueOf(fontSize.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.get().getConfig().setImageCheckInterval(Integer.valueOf(imageCheckInterval.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.get().getConfig().setIconSize(Integer.valueOf(iconSize.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.get().getConfig().setImageCheckNumber(Integer.valueOf(imageCheckNumber.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				if (new String(proxyPass.getPassword()).trim().length() > 0) {
					if (Factory.get().getSecretKey() != null) {
						Factory.get().getConfig().setProxyPass(CryptoEncrypter.encrypt(new String(proxyPass.getPassword()), Factory.get().getSecretKey()));
					} else {
						Interpreter.showError("You need to set or configure your encryption key to save the proxy password.");
						return;
					}
				} else {
					Factory.get().getConfig().setProxyPass("");
				}
				Factory.get().getConfig().save();
				Factory.get().reset();
				Factory.get().getTrayPass().loadIcon();
				Factory.get().getTrayPass().setMenu();
				dispose();
			}
		});
	}

	public String saveFile(String defaultFile) {
		String result = defaultFile;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setApproveButtonText("Select");
		chooser.setDialogTitle("Select");
		chooser.setSelectedFile(new File(defaultFile));
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			result = chooser.getSelectedFile().getAbsolutePath();
		}
		return result;
	}

	class FileAdapater extends MouseAdapter {
		private JTextField label;

		public FileAdapater(JTextField label) {
			this.label = label;
			label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			label.setToolTipText("Right click to select a file");
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == 3) {
				label.setText(saveFile(label.getText()));
			}
		}
	}

}