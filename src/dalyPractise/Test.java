package dalyPractise;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	static String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
			+ "Chrome/57.0.2987.98 Safari/537.36";
	public static void main(String[] args) {
		try {
			Document d=null;
			d=Jsoup.connect("http://www.hljhfpc.gov.cn/").userAgent(useragent).timeout(5*1000).get();
			Elements ele=d.select("#wn > div.gzdt_l.fl > div.gzdt_list > ul > li ");
			for(Element ex:ele)
			{
				System.out.println(ex.select("a").attr("href"));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
