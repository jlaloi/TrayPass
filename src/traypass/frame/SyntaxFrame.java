package traypass.frame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import traypass.Launcher;
import traypass.TrayPassObject;
import traypass.misc.TrayLabel;
import traypass.misc.TrayUpdate;
import traypass.syntax.Syntax;

public class SyntaxFrame extends JFrame {

	public SyntaxFrame() {
		setTitle("Syntax Description");
		setBackground(Color.white);
		setLayout(new GridLayout(Syntax.values().length + 5, 1));
		for (Syntax item : Syntax.getSort()) {
			JLabel label = new TrayLabel(" " + item.getExample() + " --> " + item.getDescription() + " ");
			add(label);
		}
		JLabel escape = new TrayLabel(" Escape character is " + Syntax.escapeChar);
		add(escape);
		JLabel bool = new TrayLabel(" Bools are  " + Syntax.boolTrue + " and " + Syntax.boolFalse);
		add(bool);
		JLabel example = new TrayLabel(" Middle click : Screen Capture - Double click : Stop Current Execution ");
		add(example);
		JLabel param = new TrayLabel(" Config parameter is " + Launcher.configFileNameParam + "<config name>");
		add(param);
		JLabel version = new TrayLabel(" Version : " + new TrayUpdate().getLocalVersion());
		version.setHorizontalAlignment(JLabel.RIGHT);
		add(version);
		setIconImage(TrayPassObject.trayImageIcon);
		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}
}
