package com;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static Map<AtomicInteger,String> trueWord=new ConcurrentHashMap<AtomicInteger, String>();
    private static Map<AtomicInteger,String> falseWord=new ConcurrentHashMap<AtomicInteger, String>();

    private  static AtomicInteger trueAtomicInteger=new AtomicInteger(0);
    private  static AtomicInteger falseAtomicInteger=new AtomicInteger(0);
    private  static  FileWriter trueWordText;
    private  static FileWriter falseWordText;
    static {
        try {
            trueWordText = new FileWriter("D:\\MyWork\\trueWordText.txt");
            falseWordText=new FileWriter("D:\\MyWork\\falseWordText.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        //String string="D:\\MyWork\\mytest1000.txt";
        //getWord(string);
        long startTime=System.currentTimeMillis();
        CountDownLatch countDownLatch=new CountDownLatch(8);
        List<String> fileList=ReadData("D:\\MyWork\\mytest1000.txt");
        ExecutorService executorService=Executors.newCachedThreadPool();
        Thread t1=new Thread(new MyThread(fileList.get(1),countDownLatch));
        Thread t2=new Thread(new MyThread(fileList.get(2),countDownLatch));
        Thread t3=new Thread(new MyThread(fileList.get(3),countDownLatch));
        Thread t4=new Thread(new MyThread(fileList.get(0),countDownLatch));
        Thread t5=new Thread(new MyThread(fileList.get(4),countDownLatch));
        Thread t6=new Thread(new MyThread(fileList.get(5),countDownLatch));
        Thread t7=new Thread(new MyThread(fileList.get(6),countDownLatch));
        Thread t8=new Thread(new MyThread(fileList.get(7),countDownLatch));
        executorService.execute(t1);
        executorService.execute(t2);
        executorService.execute(t2);
        executorService.execute(t3);
        executorService.execute(t4);
        executorService.execute(t5);
        executorService.execute(t6);
        executorService.execute(t7);
        executorService.execute(t8);
        countDownLatch.await();
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("运行时间："+(endTime-startTime));
    }
    public static List<String> ReadData(String filePath) throws IOException {
        List<String> fileList=new ArrayList<String>();
        FileReader fileReader=new FileReader(filePath);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String row;
        int rowNum=1;
        FileWriter fileWriter1=new FileWriter("D:\\MyWork\\text1.txt");
        FileWriter fileWriter2=new FileWriter("D:\\MyWork\\text2.txt");
        FileWriter fileWriter3=new FileWriter("D:\\MyWork\\text3.txt");
        FileWriter fileWriter4=new FileWriter("D:\\MyWork\\text4.txt");
        FileWriter fileWriter5=new FileWriter("D:\\MyWork\\text5.txt");
        FileWriter fileWriter6=new FileWriter("D:\\MyWork\\text6.txt");
        FileWriter fileWriter7=new FileWriter("D:\\MyWork\\text7.txt");
        FileWriter fileWriter8=new FileWriter("D:\\MyWork\\text8.txt");
        fileList.add("D:\\MyWork\\text1.txt");
        fileList.add("D:\\MyWork\\text2.txt");
        fileList.add("D:\\MyWork\\text3.txt");
        fileList.add("D:\\MyWork\\text4.txt");
        fileList.add("D:\\MyWork\\text5.txt");
        fileList.add("D:\\MyWork\\text6.txt");
        fileList.add("D:\\MyWork\\text7.txt");
        fileList.add("D:\\MyWork\\text8.txt");
        while ((row=bufferedReader.readLine())!=null){
            if(rowNum%8==1){
                fileWriter1.append(row+"\r\n");
            }else if(rowNum%8==2){
                fileWriter2.append(row+"\r\n");
            }else if(rowNum%8==3){
                fileWriter3.append(row+"\r\n");
            }else if(rowNum%8==4){
                fileWriter4.append(row+"\r\n");
            }else if(rowNum%8==5){
                fileWriter5.append(row+"\r\n");
            }else if(rowNum%8==6){
                fileWriter6.append(row+"\r\n");
            }else if(rowNum%8==7){
                fileWriter7.append(row+"\r\n");
            }else if(rowNum%8==0){
                fileWriter8.append(row+"\r\n");
            }
            rowNum++;
        }
        fileWriter1.close();
        fileWriter2.close();
        fileWriter3.close();
        fileWriter4.close();
        fileWriter5.close();
        fileWriter6.close();
        fileWriter7.close();
        fileWriter8.close();
        bufferedReader.close();
        return fileList;
    }
    public static void getWord(String filepath) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(filepath);
        InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line="";
        while ((line=bufferedReader.readLine())!=null){
           isWord(line);
        }
       //System.out.println(sum);
       bufferedReader.close();
       inputStreamReader.close();
       fileInputStream.close();
    }
    public static boolean isNoneWord(String inputWord) throws MalformedURLException {
        String realurl="https://baike.baidu.com/search/none?word="+inputWord;
        URL url=new URL(realurl);
        boolean flag=true;
        String input=inputWord;
        try {
            InputStream inputStream=url.openStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String data=null;
            data=bufferedReader.readLine();
            int count=0;
            while (data!=null){
                count++;
                data=bufferedReader.readLine();
                if(data.length()<(16+input.length()-3)){
                    continue;
                }
                if(data.contains("百度百科为您找到相关词条")==true){
                    flag=true;
                    //System.out.println(data);
                    break;
                }
                if(data.contains("抱歉")){
                    flag=false;
                    //System.out.println(data);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  flag;
    }
    public static boolean isWord(String inputword) throws IOException {
        boolean flag=true;
        String input=inputword;
        URL url;

            try {
                String target=null;
                //筛选出百科可以查询的词
                url = new URL("https://baike.baidu.com/item/"+input);
                InputStream inputStream=url.openStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer=new StringBuffer();
                String data=null;
                data=bufferedReader.readLine();
                int count=0;
                /**
                 * 读取页面，匹配字符
                 */
                while (data!=null&&count<100){
                    count++;
                    data=bufferedReader.readLine();
                    //对过短字符串的行，直接跳过筛选；
                    if(data.length()<(16+input.length()-3)){
                        continue;
                    }
                    //System.out.println(data);
                    //realtarget=data.substring(7,7+input.length());
                    //如果包含了input，直接返回true;
                    if(data.equalsIgnoreCase(input)){
                        flag=true;
                        System.out.println(data);
                        break;
                    }
                    //如果检测到全球最大中文百科全书，就暂时定位false,进行下一阶段筛选；
                    if(data.contains("全球最大中文百科全书")==true){
                        //百科没有改词条，但是有相关内容包含改词条，也返回为true
                        //flag=isNoneWord(inputword);
                        flag=false;
                    }
                    //System.out.println(data);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                if(flag==true){
                    trueAtomicInteger.getAndIncrement();
                    trueWord.put(trueAtomicInteger,inputword);
                    trueWordText.append(inputword+"\r\n");
                    System.out.println("true:"+trueAtomicInteger);
                }else {
                    falseAtomicInteger.getAndIncrement();
                    trueWord.put(falseAtomicInteger,inputword);
                    falseWordText.append(inputword+"\r\n");
                    //falseWordText.flush();
                    System.out.println("false:"+falseAtomicInteger);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        return flag;
    }
}
