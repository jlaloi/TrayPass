import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CryptoFrame extends JDialog {
	
	private JTextField key;
	private JLabel example;
	private JButton test, save;
	
	public CryptoFrame(){
		
		key = new JTextField();
		example = new JLabel();
		test = new JButton("Test");
		save = new JButton("OK!");
		
		setLayout(new GridLayout(3,2));
		
		add(new JLabel(" Key:"));
		add(key);
		
		add(new JLabel(" Example:"));
		add(example);
		
		add(test);
		add(save);
		
		pack();
		setTitle("Tray Encrypter Config");
		setLocationRelativeTo(getParent());
		setVisible(true);
		
		test.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				SecretKey generatedKey = CryptoEncrypter.getSecretKey(key.getText());
				String result = CryptoEncrypter.decrypt(TrayPass.trayConfig.getCryptoExample(), generatedKey);
				example.setText(result);
			}
		});

		save.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				TrayPass.key = key.getText();
				dispose();
			}
		});
		
	}

}
