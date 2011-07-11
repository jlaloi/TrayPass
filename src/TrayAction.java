import java.awt.event.InputEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrayAction {
	public static void doAction(String line) {
		String[] lines = line.split(TraySyntax.SEPARATOR.getPattern());
		for (String pass : lines) {
			pass = formatIt(pass);
			if (pass.equals(TraySyntax.WAIT)) {
				TrayTools.waitMS(1000);
			} else if (pass.equals(TraySyntax.BIGWAIT.getPattern())) {
				TrayTools.waitMS(5000);
			} else if (pass.startsWith(TraySyntax.PACK.getPattern())) {
				doAction(PackManager.getLine(pass));
			} else if (pass.startsWith(TraySyntax.NOTE.getPattern())) {
				TrayTools.addToFile(pass.substring(pass.indexOf(":") + 1), TrayTools.getClipboardContent());
			} else if (pass.startsWith(TraySyntax.SEND.getPattern())) {
				new SendKey().type(pass.substring(pass.indexOf(":") + 1));
			} else if (pass.startsWith(TraySyntax.WAITFOR.getPattern())
					|| pass.startsWith(TraySyntax.WAITFORANDCLICK.getPattern())
					|| pass.startsWith(TraySyntax.WAITFORANDRIGHTCLICK.getPattern())
					|| pass.startsWith(TraySyntax.WAITFORANDMIDDLECLICK.getPattern())) {
				String image = pass.substring(pass.indexOf(":") + 1);
				int click = 0;
				if (pass.startsWith(TraySyntax.WAITFORANDCLICK.getPattern())) {
					click = InputEvent.BUTTON1_MASK;
				} else if (pass.startsWith(TraySyntax.WAITFORANDRIGHTCLICK.getPattern())) {
					click = InputEvent.BUTTON3_MASK;
				} else if (pass.startsWith(TraySyntax.WAITFORANDMIDDLECLICK.getPattern())) {
					click = InputEvent.BUTTON2_MASK;
				}
				WaitFor wf = new WaitFor(image, click);
				for (int i = 0; i < 30 && !wf.isOnDesktop(); i++) {
					TrayPass.trayIcon.setToolTip("Looking for " + image + " (" + i + "/30)");
					TrayTools.waitMS(500);
				}
				if (!wf.isOnDesktop())
					break;
			} else if (pass.startsWith(TraySyntax.OPEN.getPattern())) {
				TrayTools.execute(pass.substring(pass.indexOf(":") + 1).split("#"));
			} else {
				if (pass.startsWith(TraySyntax.FILE.getPattern())) {
					pass = formatIt(TrayTools.getFileContent(pass.substring(pass.indexOf(":") + 1)));
				}
				TrayTools.setClipboard(pass);
			}
		}
	}

	public static String getAllInputs(String text) {
		String result = "";
		if (text.contains(TraySyntax.INPUT.getPattern())) {
			String[] parts = text.split(TraySyntax.INPUT.getPattern());
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
		Pattern p = Pattern.compile("\\" + TraySyntax.ENCRYPT.getPattern() + "\\{[^}]+\\}");
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
