package traypass.misc;

import javax.swing.JButton;

import traypass.ressources.Factory;

public class TrayButton extends JButton {

	public TrayButton() {
		super();
		setFont(Factory.get().getFont());
	}

	public TrayButton(String label) {
		super(label);
		setFont(Factory.get().getFont());
	}

}
