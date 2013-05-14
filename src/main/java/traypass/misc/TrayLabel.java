package traypass.misc;

import javax.swing.JLabel;

import traypass.ressources.Factory;

public class TrayLabel extends JLabel {

	public TrayLabel() {
		super();
		setFont(Factory.get().getFont());
	}

	public TrayLabel(String label) {
		super(label);
		setFont(Factory.get().getFont());
	}

}
