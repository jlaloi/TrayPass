package traypass.syntax.action;

import java.util.List;

import traypass.TrayPassObject;
import traypass.crypto.CryptoEncrypter;
import traypass.syntax.Action;

public class ActionDecrypt extends Action {

	public String doAction(List<String> parameters) {
		return CryptoEncrypter.decrypt(parameters.get(0), TrayPassObject.secretKey);
	}

}