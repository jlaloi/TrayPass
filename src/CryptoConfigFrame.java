import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CryptoConfigFrame extends JDialog {

	private JTextField key, example;
	private JButton save;

	public CryptoConfigFrame() {
		key = new JTextField();
		example = new JTextField();
		save = new JButton("Save");

		setLayout(new GridLayout(3, 2));

		add(new JLabel(" Your key:"));
		add(key);

		add(new JLabel(" Your test sentence:"));
		add(example);

		add(new JLabel(""));
		add(save);

		pack();
		setTitle("Tray Encrypter Config");
		setIconImage(TrayObject.trayImageIcon);
		setLocationRelativeTo(getParent());
		setVisible(true);

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				TrayObject.secretKey = CryptoEncrypter.getSecretKey(key.getText());
				TrayPass.trayConfig.setCryptoExample(CryptoEncrypter.encrypt(example.getText(), TrayObject.secretKey));
				TrayPass.trayConfig.save();
				dispose();
			}
		});
	}
}