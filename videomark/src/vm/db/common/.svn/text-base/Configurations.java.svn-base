/**
 * 
 */
package vm.db.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Administrator
 *
 */
public class Configurations {
	
	private Properties p = new Properties();
	
	private static Configurations self = null;

	private Configurations() {
		InputStream in = Configurations.class.getResourceAsStream("conf.properties");
		try {
			this.p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Configurations getInstance() {
		if (self == null) {
			self = new Configurations();
		}
		return self;
	}
	
	public String getProperty(String key) {
		return this.p.getProperty(key);
	}
}
