package toTeach;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import i3main.jdbc1;

public class Test {
	public static void main(String[] args) throws IOException {
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
					//System.out.println("Time: "+year);
					System.out.println();
					System.err.println("Title: "+title);
					System.out.println("Intlinkage: "+url1);
					insert(year,title,url1);
				}
	}

	private static void insert(String year, String title, String url1) {
		
		try {
			JdbcTest.test();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcTest.free();
		}
	}

}
