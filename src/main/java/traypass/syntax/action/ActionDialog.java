package traypass.syntax.action;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import traypass.ressources.Factory;
import traypass.syntax.Action;

public class ActionDialog extends Action {

	public String doAction(List<String> parameters) {
		String str = parameters.get(0);
		int width = Factory.get().getConfig().getCaptureWidth();
		int height = 500;
		if (parameters.size() > 1) {
			width = Integer.valueOf(parameters.get(1));
		}
		if (parameters.size() > 2) {
			height = Integer.valueOf(parameters.get(2));
		}
		JDialog dialog = new JDialog();
		if (parameters.size() > 3) {
			dialog.setTitle(parameters.get(3));
		}
		dialog.setIconImage(Factory.get().getTrayImageIcon());
		JTextArea text = new JTextArea(str);
		text.setSelectionColor(Color.GRAY);
		text.setFont(new Font(Factory.get().getConfig().getFontName(), Font.PLAIN, 13));
		JScrollPane paneScrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		paneScrollPane.setPreferredSize(new Dimension(width, height));
		dialog.add(paneScrollPane);
		dialog.pack();
		dialog.setLocationRelativeTo(dialog.getParent());
		dialog.setVisible(true);
		return str;
	}

}