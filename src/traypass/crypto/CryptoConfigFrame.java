package traypass.crypto;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import traypass.TrayPassObject;
import traypass.misc.TrayButton;
import traypass.misc.TrayLabel;
import traypass.misc.TrayTextField;

public class CryptoConfigFrame extends JDialog {

	private JTextField key, example;
	private JButton save;

	public CryptoConfigFrame() {
		key = new TrayTextField();
		example = new TrayTextField();
		save = new TrayButton("Save");

		setLayout(new GridLayout(3, 2));

		add(new TrayLabel(" Your key:"));
		add(key);

		add(new TrayLabel(" Your test sentence:"));
		add(example);

		add(new TrayLabel(""));
		add(save);

		pack();
		setTitle("Tray Encrypter Config");
		setIconImage(TrayPassObject.trayImageIcon);
		setLocationRelativeTo(getParent());
		setVisible(true);

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				TrayPassObject.secretKey = CryptoEncrypter.getSecretKey(key.getText());
				TrayPassObject.trayConfig.setCryptoExample(CryptoEncrypter.encrypt(example.getText(), TrayPassObject.secretKey));
				TrayPassObject.trayConfig.save();
				dispose();
			}
		});
	}
}