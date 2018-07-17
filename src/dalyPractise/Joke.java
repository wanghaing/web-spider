package dalyPractise;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Joke {
	static String Url="www.baidu.com";
	static String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document d=Jsoup.connect(Url).userAgent(useragent).timeout(5000).get();
			System.out.println(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
