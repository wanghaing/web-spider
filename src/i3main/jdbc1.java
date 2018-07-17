package i3main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class jdbc1 {
	private static String url="jdbc:mysql://localhost:3306/wh_data";
	private static String usre="root";
	private static String password="root";

	static void test()throws SQLException{
		//注册驱动
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());}
	
	//连接数据库
	public static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(url,usre,password);
		}
	//数据库的操作
	    /*
	    ResultSet rs=st.executeQuery("select * from user where user.title");
	    while(rs.next()){
		System.out.println(rs.getObject(1)+"\t"+rs.getObject(2)+rs.getObject(3));
		*/
	
		public static  void free(PreparedStatement ps,Connection conn)
		{
				if(ps!=null)
					try {
						ps.close();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						if(conn!=null)
							try {
								conn.close();
								
							} catch (SQLException e) {
								e.printStackTrace();
							}
					}
			}

				
}
