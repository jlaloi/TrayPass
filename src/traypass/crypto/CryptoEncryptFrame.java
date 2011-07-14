package traypass.crypto;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JTextField;

import traypass.TrayPassObject;
import traypass.tools.ToolClipboard;


public class CryptoEncryptFrame extends JDialog {

	private JTextField text, encrypted;

	public CryptoEncryptFrame() {
		text = new JTextField();
		text.setHorizontalAlignment(JTextField.CENTER);
		encrypted = new JTextField();
		encrypted.setEditable(false);
		encrypted.setHorizontalAlignment(JTextField.CENTER);

		setLayout(new GridLayout(2, 1));
		add(text);
		add(encrypted);

		setSize(800, 80);
		setTitle("Tray Encrypter Help");
		setIconImage(TrayPassObject.trayImageIcon);
		setLocationRelativeTo(getParent());
		setVisible(true);

		text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String result = "";
					if (TrayPassObject.secretKey == null) {
						result = "Encrypter not set!";
					} else {
						result = traypass.syntax.Syntax.DECRYPT.getPattern() + "{"
								+ CryptoEncrypter.encrypt(text.getText(), TrayPassObject.secretKey) + "}";
						ToolClipboard.setClipboard(result);
					}
					encrypted.setText(result);
				}
			}
		});
	}
}