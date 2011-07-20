package traypass.crypto;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JTextField;

import traypass.TrayPassObject;
import traypass.misc.TrayTextField;
import traypass.syntax.Syntax;
import traypass.tools.ToolClipboard;

public class CryptoEncryptFrame extends JDialog {

	private JTextField text, encrypted;

	public CryptoEncryptFrame() {
		text = new TrayTextField(JTextField.CENTER);
		encrypted = new TrayTextField(JTextField.CENTER);
		encrypted.setEditable(false);

		setLayout(new GridLayout(2, 1));
		add(text);
		add(encrypted);

		setSize(800, 80);
		setTitle("Tray Encrypter Help");
		setIconImage(TrayPassObject.trayImageIcon);
		setResizable(false);
		setLocationRelativeTo(getParent());
		setVisible(true);

		text.requestFocus();

		text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String result = "";
					if (TrayPassObject.secretKey == null) {
						result = "Encrypter not set!";
					} else {
						result = Syntax.DECRYPT.getPattern() + Syntax.functionParamStart + CryptoEncrypter.encrypt(text.getText(), TrayPassObject.secretKey) + Syntax.functionParamEnd;
						ToolClipboard.setClipboard(result);
					}
					encrypted.setText(result);
				}
			}
		});
	}
}