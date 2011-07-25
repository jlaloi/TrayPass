package traypass.frame;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JTextField;

import traypass.TrayPassObject;
import traypass.misc.TrayTextField;
import traypass.syntax.Syntax;
import traypass.tools.ToolClipboard;

public class SetEscapeFrame extends JDialog {

	private JTextField text, encrypted;

	public SetEscapeFrame() {
		text = new TrayTextField(JTextField.CENTER);
		encrypted = new TrayTextField(JTextField.CENTER);
		encrypted.setEditable(false);

		setLayout(new GridLayout(2, 1));
		add(text);
		add(encrypted);

		setSize(800, 80);
		setTitle("Set Escape Character Help");
		setIconImage(TrayPassObject.trayImageIcon);
		setLocationRelativeTo(getParent());
		setVisible(true);

		text.requestFocus();

		text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String result = text.getText();
					result = result.replace(Syntax.escapeChar + "", Syntax.escapeChar + "" + Syntax.escapeChar);
					result = result.replace(Syntax.functionParamEnd + "", Syntax.escapeChar + "" + Syntax.functionParamEnd);
					result = result.replace(Syntax.functionParamSeparator + "", Syntax.escapeChar + "" + Syntax.functionParamSeparator);
					result = result.replace(Syntax.functionParamStart + "", Syntax.escapeChar + "" + Syntax.functionParamStart);
					result = result.replace(Syntax.functionStart + "", Syntax.escapeChar + "" + Syntax.functionStart);
					result = result.replace(Syntax.functionSeparator + "", Syntax.escapeChar + "" + Syntax.functionSeparator);
					ToolClipboard.setClipboard(result);
					encrypted.setText(result);
				}
			}
		});

	}
}