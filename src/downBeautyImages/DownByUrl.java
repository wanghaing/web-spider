package downBeautyImages;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownByUrl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readFileByLines("E:\\ImagesAll\\Images\\imageUrls.txt");
	}
	
	public static void readFileByLines(String fileName)
	{
		File file=new File(fileName);
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(file));
			String tempString=null;
			int num=3000;
			String [][]once=new String [num][3];
			int i=0;
			while((tempString=reader.readLine())!=null)
			{
				String []a=new String[3];
				a=tempString.split("\t");
				once[i]=a;
				i++;
				if(i==num)
				{
					Thread sta=null;
					long begin=System.currentTimeMillis();
					for(i=0;i<num;i++)
					{
						DownThreadNew dt=new DownThreadNew(once[i][0],once[i][1],once[i][2]);
						sta=new Thread(dt);
						sta.start();
					}
					long end=System.currentTimeMillis();
					if(end-begin>120000)
						sta.stop();
					i=0;
				}
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println("fail to read file !");
		} catch (IOException e) {
			System.out.println("fail to read file !");
		}
	}

}
class DownThreadNew implements Runnable
{
	
	DownThreadNew(String urlStrRev, String fileNameEev, String savePathRev)
	{
		urlStr=urlStrRev;
		fileName=fileNameEev;
		savePath=savePathRev;
		
	}
	String urlStr; String fileName; String savePath;
	@Override
	public void run() 
	{
		downLoadFromUrl(urlStr, fileName, savePath);
	}
	static int successNum=0;
	public  boolean downLoadFromUrl(String urlStr, String fileName, String savePath) {
		try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(10 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			// 得到输入流
			InputStream inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);
			

			// 文件保存位置
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			File file = new File(saveDir + File.separator + fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			successNum++;
			// System.out.println("info:"+url+" download success");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public  byte[] readInputStream(InputStream inputStream) throws IOException {  
	    byte[] buffer = new byte[1024];  
	    int len = 0;  
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	    while ((len = inputStream.read(buffer)) != -1) {  
	        bos.write(buffer, 0, len);  
	    }  
	    bos.close();  
	    return bos.toByteArray();  
	} 
	
}
