package traypass.configuration;

public enum ConfigurationValues {

	MENU_FILE("Menu File", "TrayPass.txt"), 
	KEY_FILE("Keyboard key file", "key_azerty.txt"), 
	ICON_SIZE("Icon Size", 16), 
	ICON_FILE("Icon File","DefaultIcon.png"), 
	FONTNAME("Font Family", "Calibri"), 
	FONT_SIZE("Font Size", 11), 
	CRYPTO_EXAMPLE("The encrypted value of the sentence to be tested", ""), 
	FILE_ENCODING("File Encoding", "ISO-8859-1"), 
	CONSOLE_ENCODING("Console Encoding", "CP850"), 
	CAPTURE_WIDTH("Capture Frame Width",1024), 
	IMAGE_CHECK_NUMBER("Number of image check", 60), 
	IMAGE_CHECK_INTERVAL("Interval betwen each image check",450), 
	PROXY_HOST("Proxy Host", ""), 
	PROXY_PORT("Proxy Port", 8080), 
	PROXY_USER("Proxy User"), 
	PROXY_PASSWORD("Proxy Password"), 
	CAPTURE_DIR("", "", true);

	private String description;
	private Object defaultValue;
	private boolean internal;

	private ConfigurationValues(String description, Object defaultValue, boolean internal) {
		this.description = description;
		this.defaultValue = defaultValue;
		this.internal = internal;
	}

	private ConfigurationValues(String description, Object defaultValue) {
		this.description = description;
		this.defaultValue = defaultValue;
		this.internal = false;
	}

	private ConfigurationValues(String description) {
		this.description = description;
		this.defaultValue = "";
		this.internal = false;
	}

	public String getDescription() {
		return description;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public boolean isInternal() {
		return internal;
	}

}
