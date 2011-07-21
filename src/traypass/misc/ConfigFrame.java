package traypass.misc;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import traypass.TrayPassObject;
import traypass.crypto.CryptoEncrypter;
import traypass.syntax.Interpreter;

public class ConfigFrame extends JDialog {

	private JTextField cryptoKey, cryptoExample, proxyHost, proxyPort, proxyUser;
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

		save = new TrayButton("Save");

		setLayout(new GridLayout(7, 2));

		add(new TrayLabel(" Your key:"));
		add(cryptoKey);

		add(new TrayLabel(" Your test sentence:"));
		add(cryptoExample);

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

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				if (cryptoKey.getText().trim().length() > 0) {
					TrayPassObject.secretKey = CryptoEncrypter.getSecretKey(cryptoKey.getText());
					TrayPassObject.trayConfig.setCryptoExample(CryptoEncrypter.encrypt(cryptoExample.getText(), TrayPassObject.secretKey));
				}
				TrayPassObject.trayConfig.setProxyHost(proxyHost.getText());
				TrayPassObject.trayConfig.setProxyUser(proxyUser.getText());
				try {
					TrayPassObject.trayConfig.setProxyPort(Integer.valueOf(proxyPort.getText()));
				} catch (Exception e) {
					e.printStackTrace();
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
				dispose();
			}
		});
	}
}