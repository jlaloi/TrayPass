import java.awt.event.InputEvent;

import javax.swing.JOptionPane;

public class TrayAction {
	public static void doAction(String line) {
		String[] lines = line.split("\\]\\[");
		for (String pass : lines) {
			String content = pass;
			if (pass.equals("@wait")) {
				TrayTools.waitMS(1000);
			} else if (pass.equals("@bigwait")) {
				TrayTools.waitMS(5000);
			} else if (pass.startsWith("pack:")) {
				String packLine = PackManager.getLine(pass);
				doAction(packLine);
			} else if (pass.startsWith("send:")) {
				String toSend = pass.substring(pass.indexOf(":") + 1);
				new SendKey().type(toSend);
			} else if (pass.startsWith("waitfor")) {
				String image = pass.substring(pass.indexOf(":") + 1);
				int click = 0;
				if (pass.startsWith("waitforandclick:")) {
					click = InputEvent.BUTTON1_MASK;
				} else if (pass.startsWith("waitforandrightclick:")) {
					click = InputEvent.BUTTON3_MASK;
				} else if (pass.startsWith("waitforandmiddleclick:")) {
					click = InputEvent.BUTTON2_MASK;
				}
				WaitFor wf = new WaitFor(image, click);
				TrayPass.trayIcon.setToolTip("Looking for " + image);
				for (int i = 0; i < 30; i++) {
					if (wf.isOnDesktop())
						break;
					TrayTools.waitMS(500);
				}
				if (!wf.isOnDesktop())
					break;
			} else if (pass.startsWith("open:")) {
				content = getAllInputs(pass.substring(pass.indexOf(":") + 1));
				TrayTools.execute(content.split("#"));
			} else {
				if (pass.startsWith("file:")) {
					content = TrayTools.getFileContent(pass.substring(pass.indexOf(":") + 1));
				}
				TrayTools.setClipboard(getAllInputs(content));
			}
		}
	}

	public static String getAllInputs(String text) {
		String result = "";
		String patern = "@input";
		if (text.contains(patern)) {
			String[] parts = text.split(patern);
			for (int i = 0; i < parts.length; i++) {
				if (i + 1 == parts.length && parts.length > 1) {
					result += parts[i];
				} else {
					result += parts[i] + getInput();
				}
			}
		} else {
			result = text;
		}
		return result;
	}

	public static String getInput() {
		return (String) JOptionPane.showInputDialog(null, null, "Enter value", JOptionPane.PLAIN_MESSAGE, null, null, null);
	}

}
