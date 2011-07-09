import java.awt.event.InputEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrayAction {
	public static void doAction(String line) {
		String[] lines = line.split("\\]\\[");
		for (String pass : lines) {
			pass = formatIt(pass);
			if (pass.equals(TrayCMD.wait)) {
				TrayTools.waitMS(1000);
			} else if (pass.equals(TrayCMD.bigWait)) {
				TrayTools.waitMS(5000);
			} else if (pass.startsWith(TrayCMD.pack)) {
				doAction(PackManager.getLine(pass));
			} else if (pass.startsWith(TrayCMD.send)) {
				new SendKey().type(pass.substring(pass.indexOf(":") + 1));
			} else if (pass.startsWith(TrayCMD.waitFor) || pass.startsWith(TrayCMD.waitForAndClick)
					|| pass.startsWith(TrayCMD.waitForAndRightClick) || pass.startsWith(TrayCMD.waitForAndMiddleClick)) {
				String image = pass.substring(pass.indexOf(":") + 1);
				int click = 0;
				if (pass.startsWith(TrayCMD.waitForAndClick)) {
					click = InputEvent.BUTTON1_MASK;
				} else if (pass.startsWith(TrayCMD.waitForAndRightClick)) {
					click = InputEvent.BUTTON3_MASK;
				} else if (pass.startsWith(TrayCMD.waitForAndMiddleClick)) {
					click = InputEvent.BUTTON2_MASK;
				}

				TrayPass.trayIcon.setToolTip("Looking for " + image);
				WaitFor wf = new WaitFor(image, click);
				for (int i = 0; i < 30 && !wf.isOnDesktop(); i++) {
					TrayTools.waitMS(500);
				}
				if (!wf.isOnDesktop())
					break;
			} else if (pass.startsWith(TrayCMD.open)) {
				TrayTools.execute(pass.substring(pass.indexOf(":") + 1).split("#"));
			} else {
				if (pass.startsWith(TrayCMD.file)) {
					pass = formatIt(TrayTools.getFileContent(pass.substring(pass.indexOf(":") + 1)));
				}
				TrayTools.setClipboard(pass);
			}
		}
	}

	public static String getAllInputs(String text) {
		String result = "";
		if (text.contains(TrayCMD.input)) {
			String[] parts = text.split(TrayCMD.input);
			for (int i = 0; i < parts.length; i++) {
				if (i + 1 == parts.length && parts.length > 1) {
					result += parts[i];
				} else {
					result += parts[i] + TrayTools.getInput();
				}
			}
		} else {
			result = text;
		}
		return result;
	}

	public static String getDecrypt(String text) {
		String result = text;
		Pattern p = Pattern.compile("\\" + TrayCMD.encrypt + "\\{[^}]+\\}");
		Matcher m = p.matcher(text);
		while (m.find() && TrayObject.secretKey != null) {
			String encrypted = m.group();
			String toDecrypt = encrypted.substring(9, encrypted.length() - 1);
			result = result.replace(encrypted, CryptoEncrypter.decrypt(toDecrypt, TrayObject.secretKey));
		}
		return result;
	}

	public static String formatIt(String text) {
		return getAllInputs(getDecrypt(text));
	}
}
