package traypass.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import traypass.Launcher;
import traypass.misc.TrayLabel;
import traypass.misc.TrayUpdate;
import traypass.ressources.Factory;
import traypass.syntax.Syntax;

public class SyntaxFrame extends JFrame {

	public SyntaxFrame() {
		setTitle("Syntax Description");
		setBackground(Color.white);
		setLayout(new BorderLayout());

		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(Syntax.values().length, 1));
		for (Syntax item : Syntax.getSort()) {
			JLabel label = new TrayLabel(" " + item.getExample() + " --> " + item.getDescription() + " ");
			labels.add(label);
		}
		JScrollPane paneScrollPane = new JScrollPane(labels, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(900, 600));
		add(paneScrollPane, BorderLayout.NORTH);

		JPanel infos = new JPanel();
		infos.setLayout(new GridLayout(4, 1));
		JLabel escape = new TrayLabel(" Escape character is " + Syntax.escapeChar);
		infos.add(escape);
		JLabel bool = new TrayLabel(" Bools are  " + Syntax.boolTrue + " and " + Syntax.boolFalse);
		infos.add(bool);
		JLabel example = new TrayLabel(" Middle click : Screen Capture - Double click : Stop Current Execution ");
		infos.add(example);
		JLabel param = new TrayLabel(" Config parameter is " + Launcher.configFileNameParam + "<config name>");
		infos.add(param);
		add(infos, BorderLayout.CENTER);

		JLabel version = new TrayLabel(" Version : " + new TrayUpdate().getLocalVersion() + " ");
		version.setHorizontalAlignment(JLabel.RIGHT);
		add(version, BorderLayout.SOUTH);

		setIconImage(Factory.get().getTrayImageIcon());
		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}
}
