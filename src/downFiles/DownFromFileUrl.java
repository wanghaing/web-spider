package downFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DownFromFileUrl {
	private static String path="E:\\ImagesAll\\imageUrls.txt";
	public static void main(String[] args) throws Exception {
		File file=new File(path);
		InputStreamReader read = new InputStreamReader(  
                new FileInputStream(file),"utf-8");
		BufferedReader br = new BufferedReader(read);
		String line=br.readLine();
		String [][]threads=new String[10][3];
		int i=0;
		int allnum=1;
		String info[]=new String[3];
		while(line!=null)
		{
			info=line.split(",");
			i++;
			new MuchThreadDown(info[0],info[2]+"\\"+info[1],10).download();
			line=br.readLine();
		}
	}

}
