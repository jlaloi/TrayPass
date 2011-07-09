import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CryptoConfigFrame extends JDialog {

	private JTextField key, example, encryptedExample;
	private JButton save, test;

	public CryptoConfigFrame() {
		key = new JTextField();
		example = new JTextField();
		encryptedExample = new JTextField();
		save = new JButton("Save");
		test = new JButton("Test");

		setLayout(new GridLayout(4, 2));

		add(new JLabel(" Your key:"));
		add(key);

		add(new JLabel(" Your test sentense:"));
		add(example);

		add(new JLabel(" Result:"));
		add(encryptedExample);
		encryptedExample.setEditable(false);

		add(test);
		add(save);

		setSize(400, 140);
		setTitle("Tray Encrypter Config");
		setLocationRelativeTo(getParent());
		setVisible(true);

		test.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				encryptedExample.setText(getExample());
			}
		});

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				TrayPass.trayConfig.setCryptoExample(getExample());
				dispose();
			}
		});

	}
	
	private String getExample(){
		String result = "";
		SecretKey generatedKey = CryptoEncrypter.getSecretKey(key.getText());
		result = CryptoEncrypter.encrypt(example.getText(), generatedKey);
		return result;
	}

}
