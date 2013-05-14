package traypass.configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrayPassConfig implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(TrayPassConfig.class);

	private Properties properties;
	private String file;

	public TrayPassConfig(String file) {
		super();
		this.file = file;
		this.properties = new Properties();
	}
	
	public void load() {
		try {
			properties.load(new FileInputStream(file));
		} catch (Exception e) {
			logger.error("Error while loading", e);
		}
		for(ConfigurationValues configurationValues : ConfigurationValues.values()){
			if(!properties.containsKey(configurationValues.toString())){
				savePropertie(configurationValues, configurationValues.getDefaultValue());
			}
		}
	}

	public void save() {
		try {
			properties.store(new FileOutputStream(file), null);
		} catch (Exception e) {
			logger.error("Error while saving", e);
		}
	}

	public String getPropertieAsString(ConfigurationValues configurationValues) {
		return (String) properties.get(configurationValues.toString());
	}

	public int getPropertieAsInt(ConfigurationValues configurationValues) {
		return Integer.valueOf(getPropertieAsString(configurationValues));
	}

	public void savePropertie(ConfigurationValues configurationValues, Object value) {
		properties.setProperty(configurationValues.toString(), value.toString());
	}

	public String getCryptoExample() {
		return getPropertieAsString(ConfigurationValues.CRYPTO_EXAMPLE);
	}

	public void setCryptoExample(String cryptoExample) {
		savePropertie(ConfigurationValues.CRYPTO_EXAMPLE, cryptoExample);
	}

	public String getCaptureDir() {
		return getPropertieAsString(ConfigurationValues.CAPTURE_DIR);
	}

	public String getProxyPass() {
		return getPropertieAsString(ConfigurationValues.PROXY_PASSWORD);
	}

	public String getMenuFile() {
		return getPropertieAsString(ConfigurationValues.MENU_FILE);
	}

	public String getFileEncode() {
		return getPropertieAsString(ConfigurationValues.FILE_ENCODING);
	}

	public String getIconFile() {
		return getPropertieAsString(ConfigurationValues.ICON_FILE);
	}

	public String getConsoleEncode() {
		return getPropertieAsString(ConfigurationValues.CONSOLE_ENCODING);
	}

	public String getFontName() {
		return getPropertieAsString(ConfigurationValues.FONTNAME);
	}

	public String getKeyFile() {
		return getPropertieAsString(ConfigurationValues.KEY_FILE);
	}

	public int getFontSize() {
		return getPropertieAsInt(ConfigurationValues.FONT_SIZE);
	}

	public int getCaptureWidth() {
		return getPropertieAsInt(ConfigurationValues.CAPTURE_WIDTH);
	}

	public int getImageCheckInterval() {
		return getPropertieAsInt(ConfigurationValues.IMAGE_CHECK_INTERVAL);
	}

	public int getImageCheckNumber() {
		return getPropertieAsInt(ConfigurationValues.IMAGE_CHECK_NUMBER);
	}

	public int getIconSize() {
		return getPropertieAsInt(ConfigurationValues.ICON_SIZE);
	}

	public String getProxyHost() {
		return getPropertieAsString(ConfigurationValues.PROXY_HOST);
	}

	public int getProxyPort() {
		return getPropertieAsInt(ConfigurationValues.PROXY_PORT);
	}

	public String getProxyUser() {
		return getPropertieAsString(ConfigurationValues.PROXY_USER);
	}

	public void setCaptureDir(String string) {
		savePropertie(ConfigurationValues.CAPTURE_DIR, string);
	}

	public void setProxyHost(String text) {
		savePropertie(ConfigurationValues.PROXY_HOST, text);
	}

	public void setProxyUser(String text) {
		savePropertie(ConfigurationValues.PROXY_USER, text);
	}

	public void setFontName(String text) {
		savePropertie(ConfigurationValues.FONT_SIZE, text);
	}

	public void setFileEncode(String text) {
		savePropertie(ConfigurationValues.FILE_ENCODING, text);
	}

	public void setConsoleEncode(String text) {
		savePropertie(ConfigurationValues.CONSOLE_ENCODING, text);
	}

	public void setMenuFile(String text) {
		savePropertie(ConfigurationValues.MENU_FILE, text);
	}

	public void setIconFile(String text) {
		savePropertie(ConfigurationValues.ICON_FILE, text);
	}

	public void setKeyFile(String text) {
		savePropertie(ConfigurationValues.KEY_FILE, text);
	}

	public void setProxyPort(Integer valueOf) {
		savePropertie(ConfigurationValues.PROXY_HOST, valueOf);
	}

	public void setCaptureWidth(Integer valueOf) {
		savePropertie(ConfigurationValues.CAPTURE_WIDTH, valueOf);
	}

	public void setFontSize(Integer valueOf) {
		savePropertie(ConfigurationValues.FONT_SIZE, valueOf);
	}

	public void setImageCheckInterval(Integer valueOf) {
		savePropertie(ConfigurationValues.IMAGE_CHECK_INTERVAL, valueOf);
	}

	public void setIconSize(Integer valueOf) {
		savePropertie(ConfigurationValues.ICON_SIZE, valueOf);
	}

	public void setImageCheckNumber(Integer valueOf) {
		savePropertie(ConfigurationValues.IMAGE_CHECK_NUMBER, valueOf);
	}

	public void setProxyPass(String encrypt) {
		savePropertie(ConfigurationValues.PROXY_PASSWORD, encrypt);
	}

}