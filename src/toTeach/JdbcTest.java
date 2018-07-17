package toTeach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.mysql.jdbc.Driver;

public class JdbcTest {
	private static String url="jdbc:mysql://localhost:3306/test";
	private static String usre="root";
	private static String password="root";
	
	Connection conn ;
	Statement st;
	ResultSet rs;
	
	
	public static void test()throws SQLException{
	try {
		//1.注册驱动
		
	Driver driver=new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(driver);
	//System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
	//Class.forName("com.mysql.jdbc.Driver");
	
	//2.建立连接
	Connection conn =DriverManager.getConnection(url,usre,password);
	
	//3.创建语句
	Statement st=conn.createStatement();
	
	 String sql = "insert into d12(time,biaoti,wangzhi) VALUES(?,?,?)";
	// st.executeQuery(sql) ;
	
	//4.执行语句
	
	ResultSet rs =st.executeQuery("select id,year,title,url1 from d12");
	
	//5.释放资源
	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		JdbcUtils.free();
	}


	}

	public static void free() {
		// TODO Auto-generated method stub
		
	}

	
}
