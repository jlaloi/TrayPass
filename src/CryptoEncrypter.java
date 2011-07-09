
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoEncrypter {

	public static final String algorithm = "AES";

	public static String encrypt(String str, SecretKey key) {
		String result = "";
		try {
			Cipher ecipher = Cipher.getInstance(algorithm);
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			result = new sun.misc.BASE64Encoder().encode(enc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String decrypt(String str, SecretKey key) {
		String result = "";
		try {
			Cipher dcipher = Cipher.getInstance(algorithm);
			dcipher.init(Cipher.DECRYPT_MODE, key);
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			byte[] utf8 = dcipher.doFinal(dec);
			result = new String(utf8, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static SecretKey getSecretKey(String text) {
		SecretKey result = null;
		try {
			if (text == null || text.trim().length() == 0) {
				result = KeyGenerator.getInstance(algorithm).generateKey();
			} else {
				byte[] key = text.getBytes("UTF-8");
				MessageDigest sha = MessageDigest.getInstance("SHA-1");
				key = sha.digest(key);
				key = Arrays.copyOf(key, 16);
				result = new SecretKeySpec(key, algorithm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String text = "Advanced Encryption Standard ou AES (soit « standard de chiffrement avancé » en français), aussi connu sous le nom de Rijndael, est un algorithme de chiffrement symétrique. Il remporta en octobre 2000 le concours AES, lancé en 1997 par le NIST et devint le nouveau standard de chiffrement pour les organisations du gouvernement des États-Unis.";
		SecretKey key = getSecretKey("et ta soeur134");
		System.out.println(encrypt(text, key));
	}

}
