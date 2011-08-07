package traypass.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import traypass.TrayPassObject;
import traypass.crypto.CryptoEncryptFrame;
import traypass.misc.TrayButton;
import traypass.syntax.Syntax;
import traypass.tools.ToolFile;

public class EditorFrame extends JFrame {

	private JTextArea text;
	private JComboBox functions;
	private String file = "";
	public static final String newLine = "\n";

	public EditorFrame(String file) {
		this.file = file;
		setTitle("Editor Frame - " + file);

		setLayout(new BorderLayout());
		JPanel top = new JPanel();

		functions = new JComboBox();
		functions.setFont(TrayPassObject.font);
		functions.removeAll();
		for (Syntax s : Syntax.getSort()) {
			functions.addItem(s);
		}
		functions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFunction((Syntax) functions.getSelectedItem());
			}
		});
		top.add(functions);
		TrayButton test = new TrayButton("Test Selected Text");
		test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testSelected();
			}
		});
		top.add(test);
		TrayButton crypto = new TrayButton("Encrypter");
		crypto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CryptoEncryptFrame();
			}
		});
		top.add(crypto);
		TrayButton reload = new TrayButton("Reload");
		reload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		top.add(reload);
		TrayButton save = new TrayButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		top.add(save);
		TrayButton setMenu = new TrayButton("Set Menu");
		setMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (TrayPassObject.trayPass != null) {
					TrayPassObject.trayPass.setMenu();
				}
			}
		});
		top.add(setMenu);

		text = new JTextArea();
		text.setSelectionColor(Color.GRAY);
		text.setFont(new Font(TrayPassObject.fontName, Font.PLAIN, 13));
		JScrollPane paneScrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(TrayPassObject.captureWidth, TrayPassObject.captureWidth * 3 / 4));

		add(top, BorderLayout.NORTH);
		add(paneScrollPane, BorderLayout.CENTER);
		setIconImage(TrayPassObject.trayImageIcon);
		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);

		load();
	}

	private void load() {
		text.setText("");
		for (String line : ToolFile.getFileLines(file)) {
			if (text.getText().length() > 0) {
				text.setText(text.getText() + newLine + line);
			} else {
				text.setText(line);
			}
		}
	}

	private void save() {
		ToolFile.addToFile(TrayPassObject.passFile, text.getText(), false);
	}

	private void addFunction(Syntax function) {
		String str = function.getPattern() + Syntax.functionParamStart;
		for (int i = 0; i < function.getParams().length; i++) {
			if (i > 0) {
				str += function.getParams()[i] + Syntax.functionParamSeparator;
			} else {
				str += function.getParams()[i];
			}
		}
		str += Syntax.functionParamEnd;
		String result = text.getText().substring(0, text.getSelectionStart());
		result += str;
		result += text.getText().substring(text.getSelectionEnd());
		text.setText(result);
	}

	private void testSelected() {
		if (text.getSelectionStart() != text.getSelectionEnd()) {
			String exe = text.getText().substring(text.getSelectionStart(), text.getSelectionEnd());
			System.out.println("Testing: " + exe);
			if (TrayPassObject.trayPass != null) {
				TrayPassObject.trayPass.compute(exe);
			}
		}
	}

}