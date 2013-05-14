package traypass.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import traypass.misc.TrayLabel;
import traypass.misc.TrayUpdate;
import traypass.ressources.Factory;
import traypass.syntax.Function;
import traypass.syntax.Syntax;

public class SyntaxFrame extends JFrame {

	public SyntaxFrame() {
		setTitle("Syntax Description");
		setBackground(Color.white);
		setLayout(new BorderLayout());

		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(Syntax.values().length + Factory.get().getPluginManager().getPluginList().size() + 1, 1));
		for (Function item : Syntax.getSort()) {
			JLabel label = new TrayLabel(" " + getExample(item) + " --> " + item.getDescription() + " ");
			labels.add(label);
		}
		JLabel pluginLabel = new TrayLabel(" ACTION FROM PLUGIN", Factory.get().getFontBold());
		labels.add(pluginLabel);
		for (Function item : Factory.get().getPluginManager().getPluginList()) {
			JLabel label = new TrayLabel(" " + getExample(item) + " --> " + item.getDescription() + " ");
			labels.add(label);
		}

		JScrollPane paneScrollPane = new JScrollPane(labels, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(900, 600));
		add(paneScrollPane, BorderLayout.NORTH);

		JPanel infos = new JPanel();
		infos.setLayout(new GridLayout(3, 1));
		JLabel escape = new TrayLabel(" Escape character is " + Syntax.escapeChar);
		infos.add(escape);
		JLabel bool = new TrayLabel(" Bools are  " + Syntax.boolTrue + " and " + Syntax.boolFalse);
		infos.add(bool);
		JLabel example = new TrayLabel(" Middle click : Screen Capture - Double click : Stop Current Execution ");
		infos.add(example);
		add(infos, BorderLayout.CENTER);

		JLabel version = new TrayLabel(" Version : " + new TrayUpdate().getLocalVersion() + " ");
		version.setHorizontalAlignment(JLabel.RIGHT);
		add(version, BorderLayout.SOUTH);

		setIconImage(Factory.get().getTrayImageIcon());
		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}

	private String getExample(Function function) {
		String example = "";
		for (String param : function.getParams()) {
			if (example.length() > 0) {
				example += Syntax.functionParamSeparator + param;
			} else {
				example += param;
			}
		}
		example = function.getPattern() + Function.functionParamStart + example + Function.functionParamEnd;
		return example;
	}
}
