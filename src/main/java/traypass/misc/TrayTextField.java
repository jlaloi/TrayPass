package traypass.misc;

import javax.swing.JTextField;

import traypass.ressources.Factory;

public class TrayTextField extends JTextField {

	public TrayTextField() {
		super();
		setFont(Factory.font);
	}

	public TrayTextField(String label) {
		super(label);
		setFont(Factory.font);
	}

	public TrayTextField(int alignment) {
		super();
		setFont(Factory.font);
		setHorizontalAlignment(alignment);
	}

}