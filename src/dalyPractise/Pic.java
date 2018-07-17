package dalyPractise;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Pic {

	public static void main(String[] args) {
		String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";
		// TODO Auto-generated method stub
		Document d=null;
		String home="http://www.ip023.com/list/index3";
		String root="http://www.ip023.com";
		for(int j=1;j<=18;j++)
		{
			String pageUrl=home;
			if(j==1)
				pageUrl+=".html";
			else
				pageUrl+=Integer.toString(j)+".html";
			for(int i=0;i<10;i++)
			{
				try {
					d=Jsoup.connect(pageUrl).userAgent(useragent).timeout(5000).get();
					if(d!=null)
						break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Elements div=d.select("body > div:nth-child(8) > div > ul > li");
			for(Element ex:div)
			{
				String lastUrl=root+ex.select("a").attr("href");
				String caption=ex.select("a > h3").text();
				System.out.println(caption);
				System.out.println(lastUrl);
				try 
				{
					d=Jsoup.connect(lastUrl).userAgent(useragent).timeout(5000).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String endUrl=root+d.select("body > div:nth-child(8) > div > div.film_bar.clearfix > ul > ul > li > a").attr("href");
				try 
				{
					d=Jsoup.connect(endUrl).userAgent(useragent).timeout(5000).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String videoUrl=d.select(".player_1").toString();
				System.err.println(videoUrl);
			}
		}
	}

}
