package i3main;




import java.io.IOException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.ReadConfig;
import utils.SQLHelper;


public class Cho {
	public static String tableName=ReadConfig.tablename;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url="http://i3mainz.hs-mainz.de/de/suche?text=&publikationen[1]=1&nutzer=";
		String useragent="Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1";
		Document  doc = Jsoup.connect(url).userAgent(useragent).timeout(5000).get();
				Elements elements=doc.select(".view-content .accordion-heading ");
				//System.out.println(elements);
				for(Element element : elements)
				{ 
					String year=element.select("h3").text();
					String title=element.select(".accordion-title").text().trim();
                    String url1=element.select("a").attr("href").trim();
                    url1=url+url1;
					if(title.length()<20)
						continue;
					try {
					
					System.out.println("Time: "+year);
					System.out.println("���⣺"+title);
					System.out.println("Intlinkage: "+url1);
					//insert(year,title,url1);
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						System.out.println("dimhhhdsd");
						
					}
					Object[] parpm = {year,title,url1};
					String sql  = "insert into "+tableName + "(year,title,url1) values(?,?,?)";
					SQLHelper.insertBySQL(sql,parpm);
				}
					
	   }

//public static void insert(String year, String title,String url1) {
//    Connection conn = null; // 数据库连接 
//    PreparedStatement ps = null;// 数据库操作 
//    //ResultSet rs = null;
//    try {
//        conn = jdbc1.getConnection();
//        
//       String str = "insert into test(year,title,url1) VALUES(?,?,?)";//批处理
//      // Sring sql="insert into d12(year,title,url1) VALUES (2018,111,www,,com)"
//       
//        ps = conn.prepareStatement(str);//实例化PreapredStatement对象 //Statement
//        
//        ps.setString(1, year);
//        ps.setString(2, title);
//        ps.setString(3,url1);
//        
//      //执行
//        ps.execute();
//
//    } catch (Exception e) {
//    e.printStackTrace();
//    } finally {
//        jdbc1.free(ps, conn);//释放资源
//}
//}
}


