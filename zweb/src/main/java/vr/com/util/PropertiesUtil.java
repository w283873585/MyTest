package vr.com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static String getProperty(String filename, String key) {
		Properties properties = new Properties();
		InputStream stream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream(filename);
		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { stream.close(); } catch (Exception e) {}
		}
		return properties.getProperty(key);
	}
}
