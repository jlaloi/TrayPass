package traypass.syntax.action;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionDialog extends Action {

	public String doAction(List<String> parameters) {
		String str = parameters.get(0);
		int width = TrayPassObject.captureWidth;
		int height = 500;
		if (parameters.size() > 1) {
			width = Integer.valueOf(parameters.get(1));
		}
		if (parameters.size() > 2) {
			height = Integer.valueOf(parameters.get(2));
		}
		JDialog dialog = new JDialog();
		dialog.setIconImage(TrayPassObject.trayImageIcon);
		JTextArea text = new JTextArea(str);
		text.setSelectionColor(Color.GRAY);
		text.setFont(new Font(TrayPassObject.fontName, Font.PLAIN, 13));
		JScrollPane paneScrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(width, height));
		dialog.add(paneScrollPane);
		dialog.pack();
		dialog.setLocationRelativeTo(dialog.getParent());
		dialog.setVisible(true);
		return str;
	}

}