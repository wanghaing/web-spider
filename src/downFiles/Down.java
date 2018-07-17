package downFiles;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Down {
	public Down(String urlStrRev, String fileNameEev, String savePathRev)
	{
		urlStr=urlStrRev;
		fileName=fileNameEev;
		savePath=savePathRev;

	}
	static String urlStr,fileName,savePath;
	private static String path="E:\\ImagesAll\\Images\\imageUrls.txt";
	public void beginThread()
	{
		DownThread dt=new DownThread(urlStr,fileName,savePath);
		Thread sta=new Thread(dt);
		sta.start();
	}
	boolean flag;
	public static void main(String[] args) {
		File file=new File(path);
		InputStreamReader read;
		try {
			read = new InputStreamReader(  
			        new FileInputStream(file),"utf-8");
			BufferedReader br = new BufferedReader(read);
			String line=br.readLine();
			String []info=new String [3];
			String [][]threads=new String[3000][3];
			info=line.split(",");
			int i=0;
			double allNumber=0;
			while(line!=null)
			{
				allNumber++;
				threads[i]=info;
				threads[i]=info;
				threads[i]=info;
				i++;
				if(i==3000)
				{
					DownThread []d=new DownThread[3000];
					Thread []sta=new Thread[3000];
					for(i=0;i<3000;i++)
					{
						d[i]=new DownThread(threads[i][0],threads[i][1],threads[i][2]);
						sta[i]=new Thread(d[i]);
						sta[i].start();
					}
					boolean f=false;
					long start = System.currentTimeMillis(); 
					while(!f)
					{
						int []dFlag=new int[d.length];
						boolean allFlag=true;
						for(int j=0;j<d.length;j++)
						{
							d[j].flag=sta[j].isAlive();
							allFlag=allFlag&&(!d[j].flag);
							if(d[j].flag)
								dFlag[j]=1;
						}
						long end = System.currentTimeMillis(); 
						if(end-start>60000)
						{
							for(int k=0;k<d.length;k++)
							{
								if(dFlag[k]==1)
									sta[k].stop();
							
							}
							f=true;
						}
						System.err.println("succseeNumber:\t"+DownThread.successNumber);
						System.err.println("allNumber:\t"+allNumber);
						double rate=DownThread.successNumber/allNumber;
						System.out.println("failNumber\t"+DownThread.failNumber);
						System.out.println("successRate:\t"+rate*100+"%");
						if(DownThread.failNumber+DownThread.successNumber==allNumber)
							System.exit(-9);
					}
				i=0;
				}
				line=br.readLine();
				info=line.split(",");
			}
		}
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
class DownThread implements Runnable
{
	static double successNumber=0;
	static double failNumber=0;
	DownThread(String urlStrRev, String fileNameEev, String savePathRev)
	{
		urlStr=urlStrRev;
		fileName=fileNameEev;
		savePath=savePathRev;
	}
	String urlStr; String fileName; String savePath;
	@Override
	public void run() {
		flag=downLoadFromUrl(urlStr, fileName, savePath);
		if(flag)
			successNumber++;
		else 
			failNumber++;
		// TODO Auto-generated method stub
		
	}
	boolean flag;
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
			// System.out.println("info:"+url+" download success");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
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













