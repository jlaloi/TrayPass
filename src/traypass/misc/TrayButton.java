package traypass.misc;

import javax.swing.JButton;

import traypass.TrayPassObject;

public class TrayButton extends JButton {

	public TrayButton() {
		super();
		setFont(TrayPassObject.font);
	}

	public TrayButton(String label) {
		super(label);
		setFont(TrayPassObject.font);
	}

}
