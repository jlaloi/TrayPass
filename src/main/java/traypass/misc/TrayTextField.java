package traypass.misc;

import javax.swing.JTextField;

import traypass.ressources.Factory;

public class TrayTextField extends JTextField {

	public TrayTextField() {
		super();
		setFont(Factory.get().getFont());
	}

	public TrayTextField(String label) {
		super(label);
		setFont(Factory.get().getFont());
	}

	public TrayTextField(int alignment) {
		super();
		setFont(Factory.get().getFont());
		setHorizontalAlignment(alignment);
	}

}