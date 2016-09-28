package com.dumaisoft.stream;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/9/16
 * Create Time: 19:41
 * Description:
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipedStreamExam1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            //创建管道流
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);

            //创建线程对象
            Sender sender = new Sender(pos);
            Reciever reciever = new Reciever(pis);

            //运行子线程
            executorService.execute(sender);
            executorService.execute(reciever);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //等待子线程结束
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Sender extends Thread {
        private PipedOutputStream pos;

        public Sender(PipedOutputStream pos) {
            super();
            this.pos = pos;
        }

        @Override
        public void run() {
            try {
                String s = "This is a good day. 今天是个好天气。";
                System.out.println("Sender:" + s);
                byte[] buf = s.getBytes();
                pos.write(buf, 0, buf.length);
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Reciever extends Thread {
        private PipedInputStream pis;

        public Reciever(PipedInputStream pis) {
            super();
            this.pis = pis;
        }

        @Override
        public void run() {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = pis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                byte[] result = baos.toByteArray();
                String s = new String(result, 0, result.length);
                System.out.println("Reciever:" + s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}