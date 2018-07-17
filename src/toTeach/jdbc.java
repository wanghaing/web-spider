package toTeach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc {
	private static String url="jdbc:mysql://localhost:3306/test";
	private static String usre="root";
	private static String password="root";
	static void test()throws SQLException{
		//注册驱动
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	}
	//建立连接
    public static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(url,usre,password);
		}
    
	    /*
	    ResultSet rs=st.executeQuery("select*from user");
	    while(rs.next()){
		System.out.println(rs.getObject(1)+"\t"+rs.getObject(2)+rs.getObject(3));
		*/	
    
		public static  void free(ResultSet  rs,Statement st,Connection conn)
		{
		if (rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
	//
			}finally
			{
				if(st!=null)
					try {
						st.close();
						
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

}
