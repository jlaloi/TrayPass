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

import org.apache.log4j.Logger;

import traypass.TrayPassObject;
import traypass.crypto.CryptoEncrypter;
import traypass.log.LogFactory;
import traypass.misc.TrayButton;
import traypass.misc.TrayLabel;
import traypass.misc.TrayTextField;
import traypass.syntax.Interpreter;

public class ConfigFrame extends JDialog {

	private static final Logger logger = LogFactory.getLogger(ConfigFrame.class);

	private JTextField cryptoKey, cryptoExample, proxyHost, proxyPort, proxyUser, font, fontSize, captureWidth, fileEncode, consoleEncode, passFile, iconFile, imageCheckNumber, imageCheckInterval, iconSize, keyFile;
	private JPasswordField proxyPass;
	private JButton save;

	public ConfigFrame() {
		cryptoKey = new TrayTextField();
		cryptoExample = new TrayTextField();
		proxyHost = new TrayTextField(TrayPassObject.trayConfig.getProxyHost());
		proxyPort = new TrayTextField(TrayPassObject.trayConfig.getProxyPort() + "");
		proxyUser = new TrayTextField(TrayPassObject.trayConfig.getProxyUser());
		proxyPass = new JPasswordField();
		if (TrayPassObject.trayConfig.getProxyPass() != null && TrayPassObject.trayConfig.getProxyPass().trim().length() > 0) {
			if (TrayPassObject.secretKey != null) {
				proxyPass.setText(CryptoEncrypter.decrypt(TrayPassObject.trayConfig.getProxyPass(), TrayPassObject.secretKey));
			} else {
				Interpreter.showError("You need to set your encryption key to save the proxy password.");
				return;
			}
		}
		font = new TrayTextField(TrayPassObject.trayConfig.getFont());
		fontSize = new TrayTextField(TrayPassObject.trayConfig.getFontSize() + "");
		captureWidth = new TrayTextField(TrayPassObject.trayConfig.getCaptureWidth() + "");
		fileEncode = new TrayTextField(TrayPassObject.trayConfig.getFileEncode());
		consoleEncode = new TrayTextField(TrayPassObject.trayConfig.getConsoleEncode());
		passFile = new TrayTextField(TrayPassObject.trayConfig.getPassFile());
		iconFile = new TrayTextField(TrayPassObject.trayConfig.getIconFile());
		imageCheckNumber = new TrayTextField(TrayPassObject.trayConfig.getImageCheckNumber() + "");
		imageCheckInterval = new TrayTextField(TrayPassObject.trayConfig.getImageCheckInterval() + "");
		iconSize = new TrayTextField(TrayPassObject.trayConfig.getIconSize() + "");
		keyFile = new TrayTextField(TrayPassObject.keyFile);

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
		setIconImage(TrayPassObject.trayImageIcon);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);

		proxyHost.requestFocus();

		iconFile.addMouseListener(new FileAdapater(iconFile));
		passFile.addMouseListener(new FileAdapater(passFile));

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				if (cryptoKey.getText().trim().length() > 0) {
					TrayPassObject.secretKey = CryptoEncrypter.getSecretKey(cryptoKey.getText());
					TrayPassObject.trayConfig.setCryptoExample(CryptoEncrypter.encrypt(cryptoExample.getText(), TrayPassObject.secretKey));
				}
				TrayPassObject.trayConfig.setProxyHost(proxyHost.getText());
				TrayPassObject.trayConfig.setProxyUser(proxyUser.getText());
				TrayPassObject.trayConfig.setFont(font.getText());
				TrayPassObject.trayConfig.setFileEncode(fileEncode.getText());
				TrayPassObject.trayConfig.setConsoleEncode(consoleEncode.getText());
				TrayPassObject.trayConfig.setPassFile(passFile.getText());
				TrayPassObject.trayConfig.setIconFile(iconFile.getText());
				TrayPassObject.trayConfig.setKeyFile(keyFile.getText());
				try {
					TrayPassObject.trayConfig.setProxyPort(Integer.valueOf(proxyPort.getText()));
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					TrayPassObject.trayConfig.setCaptureWidth(Integer.valueOf(captureWidth.getText()));
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					TrayPassObject.trayConfig.setFontSize(Integer.valueOf(fontSize.getText()));
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					TrayPassObject.trayConfig.setImageCheckInterval(Integer.valueOf(imageCheckInterval.getText()));
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					TrayPassObject.trayConfig.setIconSize(Integer.valueOf(iconSize.getText()));
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					TrayPassObject.trayConfig.setImageCheckNumber(Integer.valueOf(imageCheckNumber.getText()));
				} catch (Exception e) {
					logger.error(e);
				}
				if (new String(proxyPass.getPassword()).trim().length() > 0) {
					if (TrayPassObject.secretKey != null) {
						TrayPassObject.trayConfig.setProxyPass(CryptoEncrypter.encrypt(new String(proxyPass.getPassword()), TrayPassObject.secretKey));
					} else {
						Interpreter.showError("You need to set or configure your encryption key to save the proxy password.");
						return;
					}
				} else {
					TrayPassObject.trayConfig.setProxyPass("");
				}
				TrayPassObject.trayConfig.save();
				TrayPassObject.compute();
				TrayPassObject.trayPass.loadIcon();
				TrayPassObject.trayPass.setMenu();
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