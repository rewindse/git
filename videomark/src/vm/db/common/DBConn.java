package vm.db.common;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	private String url = null;
	private String driver = null;
	private String user = null;
	private String password = null;
	public int numberConn;
	Connection conn;
	Connection c;
	Long time;
	
	public DBConn() {
		Configurations configs = Configurations.getInstance();
		this.url = configs.getProperty("db.url");
		this.user = configs.getProperty("db.user");
		this.driver = configs.getProperty("db.driver");
		this.password = configs.getProperty("db.password");
	}
	
	public DBConn(String url, String driver, String user, String password) {
		url = this.url;
		driver = this.driver;
		user = this.user;
		password = this.password;
	}
	
	public Connection getConn() throws Exception {
//		numberConn++;
//		if(numberConn == 15) {
//			Thread.sleep(100);
//			numberConn = 0;
//		}
		time = System.currentTimeMillis();
		if(conn==null||conn.isClosed()){
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			c = conn;
			return conn;
		}
		else{
			return c;
		}
	}
	
	public boolean release() throws Exception {
		conn.close();
		if(System.currentTimeMillis()-time>=60000){
			conn.close();
			Thread.sleep(50);
		}
		return false;
	}
	
	public static void main(String[] args) {
		DBConn f = new DBConn();
		try {
			f.getConn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
