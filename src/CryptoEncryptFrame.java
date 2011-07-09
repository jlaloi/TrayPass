import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CryptoEncryptFrame extends JDialog {

	private JTextField text, encrypted;
	private JButton button;

	public CryptoEncryptFrame() {
		text = new JTextField();
		encrypted = new JTextField();
		button = new JButton("Generate");

		setLayout(new GridLayout(3, 2));

		add(new JLabel(" To encrypte:"));
		add(text);

		add(new JLabel(" Encrypted:"));
		add(encrypted);

		add(new JLabel());
		add(button);

		setSize(400, 100);
		setTitle("Tray Encrypter Help");
		setLocationRelativeTo(getParent());
		setVisible(true);

		button.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				String keyText = TrayPass.key;
				String result = "";
				if (keyText == null || keyText.trim().length() == 0) {
					result = "Encrypter not set!";
				} else {
					SecretKey key = CryptoEncrypter.getSecretKey(TrayPass.key);
					result = "@encrypt{" + CryptoEncrypter.encrypt(text.getText(), key) + "}";
					TrayTools.setClipboard(result);
				}
				encrypted.setText(result);
			}
		});
	}

}
