package traypass.log;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogFactory {

	static {
		URL url = LogFactory.class.getResource("log4j.properties");
		PropertyConfigurator.configure(url);
	}

	public static Logger getLogger(Class c) {
		return Logger.getLogger(c);
	}

}
