package traypass.frame;

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

public class ConfigFrame extends JDialog {

	private static final Logger logger = LoggerFactory.getLogger(ConfigFrame.class);

	private JTextField cryptoKey, cryptoExample, proxyHost, proxyPort, proxyUser, font, fontSize, captureWidth, fileEncode, consoleEncode, passFile, iconFile, imageCheckNumber, imageCheckInterval, iconSize, keyFile;
	private JPasswordField proxyPass;
	private JButton save;

	public ConfigFrame() {
		cryptoKey = new TrayTextField();
		cryptoExample = new TrayTextField();
		proxyHost = new TrayTextField(Factory.trayConfig.getProxyHost());
		proxyPort = new TrayTextField(Factory.trayConfig.getProxyPort() + "");
		proxyUser = new TrayTextField(Factory.trayConfig.getProxyUser());
		proxyPass = new JPasswordField();
		if (Factory.trayConfig.getProxyPass() != null && Factory.trayConfig.getProxyPass().trim().length() > 0) {
			if (Factory.secretKey != null) {
				proxyPass.setText(CryptoEncrypter.decrypt(Factory.trayConfig.getProxyPass(), Factory.secretKey));
			} else {
				Interpreter.showError("You need to set your encryption key to save the proxy password.");
				return;
			}
		}
		font = new TrayTextField(Factory.trayConfig.getFont());
		fontSize = new TrayTextField(Factory.trayConfig.getFontSize() + "");
		captureWidth = new TrayTextField(Factory.trayConfig.getCaptureWidth() + "");
		fileEncode = new TrayTextField(Factory.trayConfig.getFileEncode());
		consoleEncode = new TrayTextField(Factory.trayConfig.getConsoleEncode());
		passFile = new TrayTextField(Factory.trayConfig.getPassFile());
		iconFile = new TrayTextField(Factory.trayConfig.getIconFile());
		imageCheckNumber = new TrayTextField(Factory.trayConfig.getImageCheckNumber() + "");
		imageCheckInterval = new TrayTextField(Factory.trayConfig.getImageCheckInterval() + "");
		iconSize = new TrayTextField(Factory.trayConfig.getIconSize() + "");
		keyFile = new TrayTextField(Factory.keyFile);

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
		setIconImage(Factory.trayImageIcon);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);

		proxyHost.requestFocus();

		iconFile.addMouseListener(new FileAdapater(iconFile));
		passFile.addMouseListener(new FileAdapater(passFile));

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				if (cryptoKey.getText().trim().length() > 0) {
					Factory.secretKey = CryptoEncrypter.getSecretKey(cryptoKey.getText());
					Factory.trayConfig.setCryptoExample(CryptoEncrypter.encrypt(cryptoExample.getText(), Factory.secretKey));
				}
				Factory.trayConfig.setProxyHost(proxyHost.getText());
				Factory.trayConfig.setProxyUser(proxyUser.getText());
				Factory.trayConfig.setFont(font.getText());
				Factory.trayConfig.setFileEncode(fileEncode.getText());
				Factory.trayConfig.setConsoleEncode(consoleEncode.getText());
				Factory.trayConfig.setPassFile(passFile.getText());
				Factory.trayConfig.setIconFile(iconFile.getText());
				Factory.trayConfig.setKeyFile(keyFile.getText());
				try {
					Factory.trayConfig.setProxyPort(Integer.valueOf(proxyPort.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.trayConfig.setCaptureWidth(Integer.valueOf(captureWidth.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.trayConfig.setFontSize(Integer.valueOf(fontSize.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.trayConfig.setImageCheckInterval(Integer.valueOf(imageCheckInterval.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.trayConfig.setIconSize(Integer.valueOf(iconSize.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				try {
					Factory.trayConfig.setImageCheckNumber(Integer.valueOf(imageCheckNumber.getText()));
				} catch (Exception e) {
					logger.error("Error", e);
				}
				if (new String(proxyPass.getPassword()).trim().length() > 0) {
					if (Factory.secretKey != null) {
						Factory.trayConfig.setProxyPass(CryptoEncrypter.encrypt(new String(proxyPass.getPassword()), Factory.secretKey));
					} else {
						Interpreter.showError("You need to set or configure your encryption key to save the proxy password.");
						return;
					}
				} else {
					Factory.trayConfig.setProxyPass("");
				}
				Factory.trayConfig.save();
				Factory.compute();
				Factory.trayPass.loadIcon();
				Factory.trayPass.setMenu();
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
		}

		public void mouseReleased(MouseEvent e) {
			label.setText(saveFile(label.getText()));
		}
	}

}