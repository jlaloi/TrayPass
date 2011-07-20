package traypass.misc;

import javax.swing.JLabel;

import traypass.TrayPassObject;

public class TrayLabel extends JLabel {

	public TrayLabel() {
		super();
		setFont(TrayPassObject.font);
	}

	public TrayLabel(String label) {
		super(label);
		setFont(TrayPassObject.font);
	}

}
