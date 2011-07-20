package traypass.misc;

import javax.swing.JTextField;

import traypass.TrayPassObject;

public class TrayTextField extends JTextField {

	public TrayTextField() {
		super();
		setFont(TrayPassObject.font);
	}

	public TrayTextField(String label) {
		super(label);
		setFont(TrayPassObject.font);
	}

	public TrayTextField(int alignment) {
		super();
		setFont(TrayPassObject.font);
		setHorizontalAlignment(alignment);
	}

}