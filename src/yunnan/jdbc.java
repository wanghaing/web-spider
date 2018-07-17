package yunnan;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public final class jdbc {
	private static String url="jdbc:mysql://localhost:3306/test";
	private static String username="root";
	private static String password="root";

	static void test() throws SQLException
	{
		//注册驱动
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	}
	
		public static java.sql.Connection getConnection() throws SQLException
		{
			return  DriverManager.getConnection(url,username,password);
		}
	
}
