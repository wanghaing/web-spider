package shanxiprivince;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.ReadConfig;
import utils.SQLHelper;

public class shanxi {
	private static String tableName = ReadConfig.tablename;
	public static void main(String[] args) throws IOException {
		
			    Document doc0=null;
		        Document doc1=null;
		        Document doc2=null;
		       
		        String ur="http://www.qywjj.gov.cn";
		        String url0="http://www.qywjj.gov.cn/article/lists/";
		        for(int i=1;i<11;i++)
		        {
		        
		        String url="http://www.shaanxi.gov.cn/info/iList.jsp?node_id=GKszfbgt&file_head=%E9%99%95%E8%A5%BF%E7%9C%81%E4%BA%BA%E6%B0%91%E6%94%BF%E5%BA%9C%E4%BB%A4&tm_id=69&cur_page="+i;
		       
		        String useragent="Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1";
		        for(int j=0;j<10;j++)
		        {
		        	try{
		        		doc0=Jsoup.connect(url).userAgent(useragent).timeout(5000).get();
		        		if(doc0!=null)
		        			break;
		        	}catch(IOException e){
		        		e.printStackTrace();
		        	}
		        }
		        Elements element1=doc0.select("body > div > div.gk_main > div.gk_list.right > table > tbody");
		     //  System.out.println(element1);
		       for(Element ele:element1)
		       {
		    	   String title=ele.select("tr > td > a").text().trim();
		    	  
		    	   String url1=ele.select("tr > td > a").attr("href");
		    	  // if(url1.length()>21)
		    		//   continue;
		    	  String temp="http://www.shaanxi.gov.cn"+url1;
		    	  
//		    	   String dispatch=ele.select("tr > td.textCenter").text();
//		    	   System.out.println(dispatch);
		    	   String time=ele.select("tr > td:nth-child(3)").text();
	    	   System.out.println(time);
	    	   System.err.println(title);
	    	   System.out.println(temp);
		       }
		      
                	   
                   }
   		        }
                  
                  // System.out.println("--------------------------------------------------------------------");
                   
		        
                 
		     
	//}

}
	
