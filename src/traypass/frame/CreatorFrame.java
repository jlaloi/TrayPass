package traypass.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import traypass.TrayPassObject;
import traypass.misc.TrayButton;
import traypass.syntax.Syntax;
import traypass.tools.ToolClipboard;

public class CreatorFrame extends JFrame {

	private JComboBox functions;
	private JTextArea line;
	private TrayButton butt;

	public CreatorFrame() {
		super("Line Creator");
		setLayout(new BorderLayout());
		functions = new JComboBox();
		functions.setFont(TrayPassObject.font);
		functions.removeAll();
		for (Syntax s : Syntax.values()) {
			functions.addItem(s);
		}
		functions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageFunction((Syntax) functions.getSelectedItem());
			}
		});
		line = new JTextArea(20, 60);
		line.setFont(TrayPassObject.font);
		line.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					ToolClipboard.setClipboard(line.getText());
				}
			}
		});
		butt = new TrayButton("Execute");
		butt.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				TrayPassObject.trayPass.compute(line.getText());
			}
		});

		add(functions, BorderLayout.NORTH);
		add(line, BorderLayout.CENTER);
		add(butt, BorderLayout.SOUTH);

		pack();
		setResizable(false);
		setIconImage(TrayPassObject.trayImageIcon);
		setLocationRelativeTo(getParent());
		setVisible(true);
	}

	private void manageFunction(Syntax selectedItem) {
		int i = 0;
		String result = selectedItem.getPattern() + Syntax.functionParamStart;
		while (i != selectedItem.getNbParameter()) {
			String arg = getText(selectedItem.getExample(), selectedItem.getDescription() + "\nParameter number " + (1 + i));
			if (arg == null || arg.trim().length() == 0) {
				break;
			}
			if (i > 0) {
				result += Syntax.functionParamSeparator + arg;
			} else {
				result += arg;
			}
			i = result.split(Syntax.functionParamSeparator + "").length;
			if (result.contains(Syntax.escapeChar + "" + Syntax.functionParamSeparator)) {
				i = i - result.split(Syntax.escapeChar + "" + Syntax.functionParamSeparator).length - 1;
			}
		}
		result += Syntax.functionParamEnd;
		if (i == selectedItem.getNbParameter() || selectedItem.getNbParameter() == -1) {
			line.setText(line.getText() + result);
		}
	}

	private String getText(String title, String text) {
		return (String) JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, null, null, null);
	}
}