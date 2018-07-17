package downFiles;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ���߳����� �� �ϵ�����
 * @author ���.
 *
 */
public class MuchThreadDown {

//  private String path = "http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3";    //����·��
    private String path = "https://github.com/git-for-windows/git/releases/download/v2.17.0.windows.1/Git-2.17.0-64-bit.exe";
    private String targetFilePath="E:\\下载\\Git-2.17.0-64-bit.exe";  //�����ļ����Ŀ¼
    private int threadCount;    //�߳�����

    /**
     * ���췽�� 
     * @param path Ҫ�����ļ�������·��
     * @param targetFilePath ���������ļ���Ŀ¼
     * @param threadCount �������߳�����,Ĭ��Ϊ 3
     */
    public MuchThreadDown(String path, String targetFilePath, int threadCount) {
        this.path = path;
        this.targetFilePath = targetFilePath;
        this.threadCount = threadCount;
    }

    /**
     * �����ļ�
     */
    public void download() throws Exception{
        //������Դ
        URL url = new URL(path);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);

        int code = connection.getResponseCode();
        if(code == 200){
            //��ȡ��Դ��С
            int connectionLength = connection.getContentLength();
            System.out.println(connectionLength);
            //�ڱ��ش���һ������Դͬ����С���ļ���ռλ
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(targetFilePath,getFileName(url)), "rw");
            randomAccessFile.setLength(connectionLength);
            /*
             * ��������������ÿ���߳�
             */
            int blockSize = connectionLength/threadCount;//����ÿ���߳����������ص�����.
            for(int threadId = 0; threadId < threadCount; threadId++){//Ϊÿ���̷߳�������
                int startIndex = threadId * blockSize; //�߳̿�ʼ���ص�λ��
                int endIndex = (threadId+1) * blockSize -1; //�߳̽������ص�λ��
                if(threadId == (threadCount - 1)){  //��������һ���߳�,��ʣ�µ��ļ�ȫ����������߳����
                    endIndex = connectionLength - 1;
                }

                new DownloadThread(threadId, startIndex, endIndex).start();//�����߳�����

            }
//          randomAccessFile.close();
        }

    }

    //���ص��߳�
    private class DownloadThread extends Thread{

        private int threadId;
        private int startIndex;
        private int endIndex;

        public DownloadThread(int threadId, int startIndex, int endIndex) {
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            System.out.println("�߳�"+ threadId + "��ʼ����");
            try {
                //�ֶ�������������,�ֶν��ļ����浽����.
                URL url = new URL(path);

                //��������λ�õ��ļ�
                File downThreadFile = new File(targetFilePath,"downThread_" + threadId+".dt");
                RandomAccessFile downThreadStream = null;
                if(downThreadFile.exists()){//����ļ�����
                    downThreadStream = new RandomAccessFile(downThreadFile,"rwd");
                    String startIndex_str = downThreadStream.readLine();
                    if(null==startIndex_str||"".equals(startIndex_str)){  //���� imonHu 2017/5/22  
                        this.startIndex=startIndex;  
                    }else{  
                        this.startIndex = Integer.parseInt(startIndex_str)-1;//�����������  
                    }
                }else{
                    downThreadStream = new RandomAccessFile(downThreadFile,"rwd");
                }

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);

                //���÷ֶ����ص�ͷ��Ϣ��  Range:���ֶ����������õġ���ʽ: Range bytes=0-1024  ���� bytes:0-1024
                connection.setRequestProperty("Range", "bytes="+ startIndex + "-" + endIndex);

                System.out.println("�߳�_"+threadId + "����������� " + startIndex + "  �����յ���: " + endIndex);

                if(connection.getResponseCode() == 206){//200������ȫ����Դ�ɹ��� 206��������Դ����ɹ�
                    InputStream inputStream = connection.getInputStream();//��ȡ��
                    RandomAccessFile randomAccessFile = new RandomAccessFile(
                            new File(targetFilePath,getFileName(url)), "rw");//��ȡǰ���Ѵ������ļ�.
                    randomAccessFile.seek(startIndex);//�ļ�д��Ŀ�ʼλ��.


                    /*
                     * ���������е��ļ�д�뱾��
                     */
                    byte[] buffer = new byte[1024];
                    int length = -1;
                    int total = 0;//��¼���������ļ��Ĵ�С
                    while((length = inputStream.read(buffer)) > 0){
                        randomAccessFile.write(buffer, 0, length);
                        total += length;
                        /*
                         * ����ǰ���ڵ���λ�ñ��浽�ļ���
                         */
                        downThreadStream.seek(0);
                        downThreadStream.write((startIndex + total + "").getBytes("UTF-8"));
                    }

                    downThreadStream.close();
                    inputStream.close();
                    randomAccessFile.close();                   
                    cleanTemp(downThreadFile);//ɾ����ʱ�ļ�
                    System.out.println("�߳�"+ threadId + "�������");
                }else{
                    System.out.println("��Ӧ����" +connection.getResponseCode() + ". ��������֧�ֶ��߳�����");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //ɾ���̲߳�������ʱ�ļ�
    private synchronized void cleanTemp(File file){
        file.delete();
    }

    //��ȡ�����ļ�������
    private String getFileName(URL url){
        String filename = url.getFile();
        return filename.substring(filename.lastIndexOf("/")+1);
    }

    public static void main(String[] args) {
        try {
            new MuchThreadDown("https://github.com/git-for-windows/git/releases/download/v2.17.0.windows.1/Git-2.17.0-64-bit.exe", "E:\\下载\\Git-2.17.0-64-bit.exe",10 ).download();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
