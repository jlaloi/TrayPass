import java.awt.event.InputEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class TrayAction {
	public static void doAction(String line) {
		String[] lines = line.split("\\]\\[");
		for (String pass : lines) {
			pass = formatIt(pass);
			if (pass.equals("@wait")) {
				TrayTools.waitMS(1000);
			} else if (pass.equals("@bigwait")) {
				TrayTools.waitMS(5000);
			} else if (pass.startsWith("pack:")) {
				doAction(PackManager.getLine(pass));
			} else if (pass.startsWith("send:")) {
				new SendKey().type(pass.substring(pass.indexOf(":") + 1));
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
				TrayTools.execute(pass.substring(pass.indexOf(":") + 1).split("#"));
			} else {
				if (pass.startsWith("file:")) {
					pass = formatIt(TrayTools.getFileContent(pass.substring(pass.indexOf(":") + 1)));
				}
				TrayTools.setClipboard(pass);
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
	
	public static String getDecrypt(String text){
		String result = text;
		Pattern p = Pattern.compile("\\@encrypt\\{(.)+\\}");
		Matcher m = p.matcher(text);
		while(m.find() && TrayPass.key != null){
			String encrypted = m.group();
			String toDecrypt = encrypted.substring(9, encrypted.length() -1);
			result = result.replace(encrypted, CryptoEncrypter.decrypt(toDecrypt, TrayPass.key));
		}
		return result;
	}
	
	public static String formatIt(String text){
		return getDecrypt(getAllInputs(text));
	}

}
