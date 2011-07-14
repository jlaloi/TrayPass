package traypass.syntax.action;

import traypass.TrayPassObject;
import traypass.crypto.CryptoEncrypter;
import traypass.syntax.Action;

public class ActionDecrypt extends Action {

	public String execute(Object... parameter) {
		return CryptoEncrypter.decrypt((String) parameter[0], TrayPassObject.secretKey);
	}

}