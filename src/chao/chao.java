package chao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class chao {
	
	public static void main(String[] args)   {
		int h=0;
		String urlmain="http://www.ahep.com.cn";
		String  url = "http://www.ahep.com.cn/down.html";//主网站
		

			Document doc=null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			Elements lis=doc.select(".line1 div a");
			
			for(Element e:lis){
				int j=1,k;
		
				String url1=e.attr("href").trim();
				String url2=urlmain+url1+"&pageno=";
				System.out.println(url2);
				if(j==3)
					k=1;
				else
					k=5;
				for(int i=1;i<=k;i++)
				{
					Document doc1=null;
					try {
						doc1 = Jsoup.connect(url2+i).get();
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
		
					Elements lis1 = doc1.select("#nei_right table[bgcolor=#CCCCCC] tbody tr");
//					System.out.println(lis1);
		
					for(Element e1:lis1){
						
						String title=e1.select("a").text();
						String url3=urlmain+"/"+e1.select("a").attr("href").trim();
						String size=e1.select("td").get(3).text();
						String time=e1.select("td").get(4).text();
						if(url3.length()<=25)
							continue;
					
						System.out.println(title);
						System.out.println(url3);
						System.out.println(size);
						System.out.println(time);
						

					String sql="insert into tt (url) values (' "+title+" ') ";
					try {
						zheng.create(sql);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						System.err.println("jdbc异常");
						e2.printStackTrace();
					}
					}
			
				}
				j++;
			}
			

		
	}

}
