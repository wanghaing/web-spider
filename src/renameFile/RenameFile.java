package renameFile;

import  java.io.*;
import java.util.ArrayList;   
    
public   class   RenameFile 
{   
        public   static   void   main(String[]   args)   {  
        	Rename();
        }  
        public static void Rename()
        {
        	ArrayList<String>a=getFiles("C:\\Users\\My laptop\\Desktop\\201608040117史谨");
        	for(int i=0;i<a.size();i++)
	        {
	        	File   file=new   File(a.get(i));   //指定文件名及路径
	            String name=Integer.toString(i+1);    
	            file.renameTo(new File(name+".png","C:\\Users\\My laptop\\Desktop\\201608040117史谨"));
        	}
        	
        }
        public static ArrayList<String> getFiles(String path) {
            ArrayList<String> files = new ArrayList<String>();
            File file = new File(path);
            File[] tempList = file.listFiles();

            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
                    //  System.out.println("文     件：" + tempList[i]);
                    files.add(tempList[i].toString());
                }
                if (tempList[i].isDirectory()) {
                     // System.out.println("文件夹：" + tempList[i]);
                }
            }
            return files;
        }
    
}