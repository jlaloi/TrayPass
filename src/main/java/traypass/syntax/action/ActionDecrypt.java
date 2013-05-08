package traypass.syntax.action;

import java.util.List;

import traypass.crypto.CryptoEncrypter;
import traypass.ressources.Factory;
import traypass.syntax.Action;

public class ActionDecrypt extends Action {

	public String doAction(List<String> parameters) {
		return executeParam(CryptoEncrypter.decrypt(parameters.get(0), Factory.secretKey));
	}

}