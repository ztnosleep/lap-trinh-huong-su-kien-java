package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() throws SQLException {
		instance.connect();
		return instance;
	}
	public void connect() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=CuaHangTienLoi_VF";
		String username = "sa";
		String pwd = "thaidui";
		con = DriverManager.getConnection(url, username, pwd);
	}
	public void disconnect() {
		if(con!=null) {
			try {
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	public static Connection getConnection() {
		return con;
	}
}
