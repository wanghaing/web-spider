package downBeautyImages;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Second {
	
	public static void main(String[] args) {
		Document d=null;
		String useragent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";
		// TODO Auto-generated method stub
		String url="http://www.mmonly.cc/mmtp/list_9_";
		int num=5;
		for(int page=1;page<=783;page++)
		{
			url=url+Integer.toString(page)+".html";
			try 
			{
				d=Jsoup.connect(url).userAgent(useragent).timeout(5000).get();
				Elements e=d.select("#infinite_scroll > div > div.item_t > div > div.ABox > a");
				for(Element ex:e)
				{
					String pageUrl=ex.attr("href");
					String folderName=ex.select("img").attr("alt");
					d=Jsoup.connect(pageUrl).userAgent(useragent).timeout(5000).get();
					String tem=d.select("body > div:nth-child(2) > div.photo > div.wrapper"
							+ ".clearfix.imgtitle > div.pages > ul > li:nth-child(1) > a").text();
					int imgNum=Integer.parseInt(tem.substring(1, tem.length()-2));
					String imgUrl;
					String temPageUrl=pageUrl.substring(0, pageUrl.length()-5);
					for(int i=1;i<=imgNum;i++)
					{
						imgUrl=null;
						if(i==1)
						{
							imgUrl=d.select("#big-pic > p > a > img").attr("src");
						}
						else 
						{
							pageUrl=temPageUrl+"_"+Integer.toString(i)+".html";
							d=Jsoup.connect(pageUrl).userAgent(useragent).timeout(5000).get();
							imgUrl=d.select("#big-pic > p > a > img").attr("src");
						}
						if(!imgUrl.isEmpty())
						{
							DownUrlThread du=new DownUrlThread("E:\\ImagesAll\\Images\\imageUrls.txt",imgUrl+"\t"+Integer.toString(i)+".jpg"+"\t"+"E:\\ImagesAll\\Images\\second\\"+folderName+"\n");
							Thread sta=new Thread(du);
							sta.start();
						}
						
//						DownThread dt=new DownThread(imgUrl,Integer.toString(i)+".jpg","E:\\ImagesAll\\Images\\second\\"+folderName);
//						Thread sta=new Thread(dt);
//						sta.start();
//						successNum=dt.successNum;
					}
				}
			}
			catch (IOException e) {
				System.out.println(url);
				
			}
			url="http://www.mmonly.cc/mmtp/list_9_";
			
		}
	}
	

}
class DownUrlThread implements Runnable
{
	DownUrlThread(String fileRev,String contentRev)
	{
		file=fileRev;
		content=contentRev;
	}
	String file,content;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		method1(file, content);
	}
	public void method1(String file, String conent) {     
        BufferedWriter out = null;     
        try {     
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));     
            out.write(conent);     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(out != null){  
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }     
    }  
}

