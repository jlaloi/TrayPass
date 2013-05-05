package traypass.syntax.action;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;

import traypass.syntax.Action;
import traypass.tools.TextReader;
import traypass.tools.ToolImage;

public class ActionTextReader extends Action {

	public String doAction(List<String> parameters) {
		String result = null;
		BufferedImage image = ToolImage.loadImage(parameters.get(0));
		Font font = new Font(parameters.get(1), Integer.valueOf(parameters.get(2)), Integer.valueOf(parameters.get(3)));
		TextReader tr = new TextReader(image, font);
		if (parameters.size() > 4) {
			tr.setSpecialChar(parameters.get(4));
		}
		if (parameters.size() > 5) {
			tr.setFontColor(Integer.valueOf(parameters.get(5)));
		}
		result = tr.read();
		return result;
	}

}
