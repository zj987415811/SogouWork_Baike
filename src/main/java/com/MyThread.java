package com;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MyThread implements Runnable{
    private  String filePath;
    private CountDownLatch countDownLatch;
    public MyThread(String filePath,CountDownLatch countDownLatch) {
        this.filePath=filePath;
        this.countDownLatch=countDownLatch;
    }
    public void run() {
        try {
            Main.getWord(filePath);
            countDownLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
