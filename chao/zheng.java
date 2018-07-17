package chao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class zheng {


		static void create(String sql) throws SQLException {
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			try {
				// 2.建立连接
				conn = JdbcUtils.getConnection();
				// conn = JdbcUtilsSing.getInstance().getConnection();
				// 3.创建语句
				st = conn.createStatement();

//				String sql = "insert into user(name,birthday, money) values ('name11', '1987-01-011', 4001) ";

				// 4.执行语句
				int i = st.executeUpdate(sql);

				System.out.println("i=" + i);
			} finally {
				JdbcUtils.free(rs, st, conn);
			}
		}

}
