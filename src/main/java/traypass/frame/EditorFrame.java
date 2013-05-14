package traypass.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import traypass.crypto.CryptoEncryptFrame;
import traypass.misc.TrayButton;
import traypass.ressources.Factory;
import traypass.syntax.Function;
import traypass.syntax.Syntax;
import traypass.syntax.action.ActionSend;
import traypass.syntax.plugin.Plugin;
import traypass.tools.ToolClipboard;
import traypass.tools.ToolFile;

public class EditorFrame extends JFrame {

	private JTextArea text;
	private JTextField keyCode;
	private JComboBox functions;
	private String file = "";
	public static final String newLine = "\n";

	public EditorFrame(String file) {
		this.file = file;
		setTitle("Editor Frame - " + file);

		setLayout(new BorderLayout());
		JPanel top = new JPanel();

		functions = new JComboBox();
		functions.setFont(Factory.get().getFont());
		functions.removeAll();
		for (Syntax s : Syntax.getSort()) {
			functions.addItem(s);
		}
		for (Plugin plugin : Factory.get().getPluginManager().getPluginList()) {
			functions.addItem(plugin);
		}
		functions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFunction((Function) functions.getSelectedItem());
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

		keyCode = new JTextField();
		keyCode.setToolTipText("Type to get the key code, right click to put in clipboard");
		keyCode.setEditable(false);
		keyCode.setPreferredSize(new Dimension(80, keyCode.getPreferredSize().height));
		keyCode.setFont(Factory.get().getFont());
		keyCode.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				keyCode.setText(ActionSend.keyCodeString + e.getKeyCode() + "}");
			}

			public void keyPressed(KeyEvent e) {
			}
		});
		keyCode.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 3) {
					ToolClipboard.setClipboard(keyCode.getText());
				}
			}
		});
		top.add(keyCode);

		top.add(reload);
		TrayButton setMenu = new TrayButton("Save");
		setMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				if (Factory.get().getTrayPass() != null) {
					Factory.get().getTrayPass().setMenu();
				}
				dispose();
			}
		});
		top.add(setMenu);

		text = new JTextArea();
		text.setSelectionColor(Color.GRAY);
		text.setFont(new Font(Factory.get().getConfig().getFontName(), Font.PLAIN, 13));
		JScrollPane paneScrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(Factory.get().getConfig().getCaptureWidth(), 700));

		add(top, BorderLayout.NORTH);
		add(paneScrollPane, BorderLayout.CENTER);
		setIconImage(Factory.get().getTrayImageIcon());
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
		ToolFile.addToFile(Factory.get().getConfig().getMenuFile(), text.getText(), false);
	}

	private void addFunction(Function function) {
		String str = function.getPattern() + Function.functionParamStart;
		for (int i = 0; i < function.getParams().length; i++) {
			if (i > 0) {
				str += function.getParams()[i] + Function.functionParamSeparator;
			} else {
				str += function.getParams()[i];
			}
		}
		str += Function.functionParamEnd;
		String result = text.getText().substring(0, text.getSelectionStart());
		result += str;
		result += text.getText().substring(text.getSelectionEnd());
		text.setText(result);
	}

	private void testSelected() {
		if (text.getSelectionStart() != text.getSelectionEnd()) {
			String exe = text.getText().substring(text.getSelectionStart(), text.getSelectionEnd());
			System.out.println("Testing: " + exe);
			if (Factory.get().getTrayPass() != null) {
				Factory.get().getTrayPass().compute(exe);
			}
		}
	}

}