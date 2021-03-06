package traypass.crypto;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.crypto.SecretKey;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import traypass.misc.TrayLabel;
import traypass.ressources.Factory;

public class CryptoEnterFrame extends JDialog {

	private JPasswordField key;
	private JLabel example;

	public CryptoEnterFrame() {
		key = new JPasswordField();
		key.setHorizontalAlignment(JPasswordField.CENTER);
		example = new TrayLabel();
		example.setHorizontalAlignment(JLabel.CENTER);

		setLayout(new GridLayout(2, 1));
		add(key);
		add(example);

		setSize(200, 80);
		setTitle("Enter your key");
		setIconImage(Factory.get().getTrayImageIcon());
		setLocationRelativeTo(getParent());
		setAlwaysOnTop(true);
		setResizable(false);
		setVisible(true);

		key.requestFocus();

		key.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String keyp = new String(key.getPassword());
				SecretKey generatedKey = CryptoEncrypter.getSecretKey(keyp);
				String result = CryptoEncrypter.decrypt(Factory.get().getConfig().getCryptoExample(), generatedKey);
				example.setText(result);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (example.getText().trim().length() > 0) {
						Factory.get().setSecretKey(generatedKey);
						dispose();
					}
				}
			}
		});
	}
}