package traypass.syntax.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import traypass.log.LogFactory;
import traypass.syntax.Action;

import com.sun.awt.AWTUtilities;

public class ActionQuickFrame extends Action {

	private static final Logger logger = LogFactory.getLogger(ActionQuickFrame.class);

	private float level = 1;

	public String doAction(List<String> parameters) {
		String text = parameters.get(0);
		int seconds = 5;
		if (parameters.size() > 1) {
			seconds = Integer.valueOf(parameters.get(1));
		}
		Color color = Color.red;
		if (parameters.size() > 4) {
			int r = Integer.valueOf(parameters.get(2));
			int g = Integer.valueOf(parameters.get(3));
			int b = Integer.valueOf(parameters.get(4));
			color = new Color(r, g, b);
		}
		JDialog jDialog = new JDialog();
		JLabel label = new JLabel(text);
		label.setFont(new Font("Calibri", Font.BOLD, 22));
		label.setForeground(color);
		jDialog.add(label);
		jDialog.setFocusableWindowState(false);
		jDialog.setFocusable(false);
		jDialog.setUndecorated(true);
		jDialog.pack();
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		jDialog.setLocation(screenWidth - jDialog.getWidth(), screenHeight - jDialog.getHeight() - 50);
		jDialog.setAlwaysOnTop(true);
		jDialog.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				level = 1;
			}
		});
		try {
			AWTUtilities.setWindowOpaque(jDialog, false);
			jDialog.setVisible(true);
			Thread.sleep(1000 * seconds);
			for (; level > 0; level -= .03) {
				AWTUtilities.setWindowOpacity(jDialog, level);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			logger.error(e);
		}
		jDialog.dispose();
		return text;
	}

}
