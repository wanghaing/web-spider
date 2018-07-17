package suizhou;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.ReadConfig;
import utils.SQLHelper;

public class suizhou {
	public static String tableName=ReadConfig.tablename;

	public static void main(String[] args) {
		
		Document doc0=null;
		Document doc1=null;
		
		String home ="http://wjw.suizhou.gov.cn";
		String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";
		
		String []Urls=
			{
					
					//"http://wjw.suizhou.gov.cn/news/list-61-",
					"http://wjw.suizhou.gov.cn/news/list-28-"
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政字&tm_id=69&cur_page=",
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政任字&tm_id=69&cur_page=",
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政通报&tm_id=69&cur_page=",
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政函&tm_id=69&cur_page=",
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政办发&tm_id=69&cur_page=",
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政办字&tm_id=69&cur_page=",
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政办发明电&tm_id=69&cur_page=",
//				"http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=陕政办函&tm_id=69&cur_page="
			};
		
		//int []Pages={18,116,67,55,143,6,14,53};
		int []Pages= {600,240};
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
						//System.out.println(doc0);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				Elements ele1=doc0.select("body > div.p_fw_xz > div.contR.fl > div.wy_list > div.wy_contMain.fontSt > ul ");
				//body > div.p_fw_xz > div.contR.fl > div.wy_list > div.wy_contMain.fontSt > ul 
				//body > div.p_fw_xz > div.contR.fl > div.wy_list > div.wy_contMain.fontSt > ul > li:nth-child(1) > a
				//body > div.p_fw_xz > div.contR.fl > div.wy_list > div.wy_contMain.fontSt > ul > li > a
				for(Element ele :ele1)
				{
					String url=ele.select("ul>li>a").attr("href");
					//String title=ele.select("ul>li>a").text();
					//System.err.println(title);
					System.out.println();
					
				//	String url=home+temp;
					
					System.out.println(url);
					
					//System.err.println(url);
					//String time=ele.select("li>span").text();
					//System.out.println(dispatch);
					// String title=ele.select("td:nth-child(3)").text();
					// System.out.println(title);
					// String time=ele.select(" td:nth-child(3)").text();
					// System.out.println(time);
					try {
						doc1=Jsoup.connect(url).userAgent(useragent).timeout(5000).get();
						//System.out.println(doc1);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//body > div.wy_dis_wiap > div.wy_dis_main > h1
	//				String index=doc1.select("body > div:nth-child(4) > div.xzfwj_box > div.xzfwj_lef > div > div.zfwj_news_table > table > tbody > tr:nth-child(1) > td.gk_news_table_title").text();
//					String dispath=doc1.select("body > div:nth-child(4) > div.xzfwj_box > div.xzfwj_lef > div > div.zfwj_news_table > table > tbody > tr:nth-child(2) > td.gk_news_table_title").text();
//					String title=doc1.select("body > div:nth-child(4) > div.xzfwj_box > div.xzfwj_lef > div > div.zfwj_news_table > table > tbody > tr:nth-child(3) > td.gk_news_table_title").text();
//					System.out.println();
				    String caption=doc1.select("body > div.wy_dis_wiap > div.wy_dis_main > div.wy_contMain.fontSt > div").text();
				    System.err.println(caption);
					String code=doc1.select("body > div.wy_dis_wiap > div.wy_dis_main > div.wy_contMain.fontSt").html();
					String title=doc1.select("body > div.wy_dis_wiap > div.wy_dis_main > h1").text().trim();
					String time=doc1.select("body > div.wy_dis_wiap > div.wy_dis_main > div.time.f12.fontSt").text();
					String temp1=time.substring(6,16);
					String freq=time.substring(23,26);
					//String orign=time.substring(17,20);
					String orign="市卫计委";
					//System.out.println(freq);
					
					//System.err.println(orign);
					
					Object[] parpm = {title,url,code,caption,freq,orign};
					String sql  = "insert into "+tableName + "(title,url,code,caption,,freq,orign) values(?,?,?,?,?,?)";
					SQLHelper.insertBySQL(sql,parpm);
				}
			}
		}
		
		
	}

}
