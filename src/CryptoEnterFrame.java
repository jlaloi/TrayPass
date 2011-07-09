import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.crypto.SecretKey;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class CryptoEnterFrame extends JDialog {

	private JPasswordField key;
	private JLabel example;

	public CryptoEnterFrame() {
		key = new JPasswordField();
		key.setHorizontalAlignment(JPasswordField.CENTER);
		example = new JLabel();
		example.setHorizontalAlignment(JLabel.CENTER);

		setLayout(new GridLayout(2, 1));
		add(key);
		add(example);

		setSize(200, 80);
		setTitle("Enter your key");
		setIconImage(TrayObject.trayImageIcon);
		setLocationRelativeTo(getParent());
		setVisible(true);

		key.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String keyp = new String(key.getPassword());
				SecretKey generatedKey = CryptoEncrypter.getSecretKey(keyp);
				String result = CryptoEncrypter.decrypt(TrayObject.trayConfig.getCryptoExample(), generatedKey);
				example.setText(result);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (example.getText().trim().length() > 0) {
						TrayObject.secretKey = generatedKey;
						dispose();
					}
				}
			}
		});
	}
}