package yunnan;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.ReadConfig;
import utils.SQLHelper;

public class yunan {
	private static String tableName = ReadConfig.tablename;
	public static void main(String[] args) {
		
		Document doc0=null;
		Document doc1=null;
		Document doc2=null;
		String home ="http://www.shaanxi.gov.cn";
		String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";
		
		String []Urls=
			{
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/zfl/index_257_",
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzf/index_257_",
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzh/index_257_",
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yz/index_257_",
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzr/index_257_",
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzbf/index_257_",
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzbh/index_257_",
					"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/bmd/index_257_"
				
			};
		
		int []Pages={3,22,2,6,16,27,10,2};
		int num=0;
		
		for(int i=0;i<Urls.length;i++)
		{
			for(int k=1;k<Pages[i];k++)
			{
				for(int j=0;j<10;j++)
				{
					try {
						doc0=Jsoup.connect(Urls[i]+Integer.toString(k)+".html").userAgent(useragent).timeout(5000).get();
						if(doc0!=null)
							break;
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				Elements ele1=doc0.select("body > div.Content > div.Part_1 > ul > li");
				for(Element ele :ele1)
				{
//					
//					String []Urls1=
//						{
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/zfl",
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzf",
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzh",
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yz",
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzr",
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzbf",
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/yzbh",
//								"http://www.yn.gov.cn/yn_zwlanmu/qy/wj/bmd"	
//						};
//					int []nums= {23,399,16,76,266,475,170,10};
//					for(int j=0;j<Urls1.length;j++)
//					{
//						for(int m=0;k<nums[j];m++)
//						{
				String tem=ele.select("a").attr("href").substring(1);
				String url="http://www.yn.gov.cn/yn_zwlanmu/qy/wj/zfl"+tem;
					if(url.contains("pdf"))
						continue;
					
					//System.err.println(url);
					
				   // String title=ele.select("a").text();
				//	System.out.println(title);
						
					try {
						doc1=Jsoup.connect(url).userAgent(useragent).timeout(5000).get();
						//System.out.println(doc1);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					String t=doc1.select("body > div.Content > div > div.zoom > div > div:nth-child(1) > font").text();
					String content=doc1.select("body > div.Content > div > div.zoom > div > div:nth-child(2) > div > div").text();
					System.out.println(content);
					System.err.println(t);
					String temp=doc1.select("body > div.Content > div > table > tbody > tr > td:nth-child(2)").text();
					
				    String wenhao=temp.substring(0,20);
				    String orign=temp.substring(21);
					String ti=doc1.select("body > div.Content > div > table > tbody > tr:nth-child(2) > td:nth-child(4)").text();
					String code=doc1.select("body > div.Content > div > div.zoom").html();
					System.out.println(code);
//					System.out.println(ti);
//					System.out.println(wenhao);
//					System.out.println(orign);
					Object[] parpm = {t,wenhao,code,url,ti,orign};
					String sql  = "insert into "+tableName + "(t,wenhao,code,url,ti,orign) values(?,?,?,?,?,?)";
					SQLHelper.insertBySQL(sql,parpm);
					
					
						}
				}
			
			
   }
		

		
	
		
	}
}
