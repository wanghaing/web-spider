package shanxiprivince;

import utils.ReadConfig;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.ReadConfig;
import utils.SQLHelper;

public class Test {
	static String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";
	private  String tableName = ReadConfig.tablename;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="http://www.shuangyashan.gov.cn/index/html/newslist/newslist_one_dw.jsp?lmid=254&dwid=39";
		String home="http://www.shuangyashan.gov.cn/index/html/newslist/newslist_one_dw.jsp?";
		String home1="http://www.shuangyashan.gov.cn";
		Document d=null;
		for(int connectTime=0;connectTime<10;connectTime++)
		{
			try
			{
				d=Jsoup.connect(url).userAgent(useragent).timeout(5000).get();
				if(d!=null)
					break;
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//currentPage=2&lmid=254&dwid=39&seach=1
		//currentPage=3&lmid=254&dwid=39&seach=2
		for(int i=1;i<=39;i++)
		{
			String pageUrl=home+"currentPage="+Integer.toString(i)+"&lmid=254&dwid=39&seach="+Integer.toString(i-1);
			try
			{
				d=Jsoup.connect(url).userAgent(useragent).timeout(5000).get();
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Elements rows=d.select("body > div.contain > form > ul > li");
			for(Element row:rows)
			{
				String rowUrl=home1+row.select("li > a").attr("href");
				Thread sta=new Thread(new MuchGetThis(rowUrl,row));
				sta.start();
			}
		}
		
	}

}
class MuchGetThis implements Runnable
{
	private  String tableName = ReadConfig.tablename;
	private String rowUrl;
	private Element row;
	Document d0;
	static String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";
	String captionSelector="li> a";
	String codeSelector="body > div.contain > div";
	String dateSelector="li > span";
	public MuchGetThis(String rowUrlRev,Element rowRev)
	{
		rowUrl=rowUrlRev;
		row=rowRev;
	}
	//body > div.contain > form > ul > li > span
	public void run() {
		// TODO Auto-generated method stub
		String date=row.select(dateSelector).text();
		String caption=row.select(captionSelector).text();
		try
		{
			d0=Jsoup.connect(rowUrl).userAgent(useragent).timeout(5000).get();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text=d0.select(codeSelector).text();
		Object[] parpm = {caption,text,date,d0.select(codeSelector).toString(),rowUrl};
		String sql  = "insert into "+tableName + "(caption,content,date,code,address) values(?,?,?,?,?)";
		SQLHelper.insertBySQL(sql,parpm);
	}
	
}