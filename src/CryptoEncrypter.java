
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
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
			if(str != null && str.trim().length() > 3){
				Cipher dcipher = Cipher.getInstance(algorithm);
				dcipher.init(Cipher.DECRYPT_MODE, key);
				byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
				byte[] utf8 = dcipher.doFinal(dec);
				result = new String(utf8, "UTF8");
			}
		}catch (BadPaddingException be){
			System.out.println("Wrong KEY");
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

}
