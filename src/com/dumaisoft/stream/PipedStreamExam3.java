package com.dumaisoft.stream;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/9/16
 * Create Time: 19:42
 * Description:
 */
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipedStreamExam3 {
    public static void main(String[] args) {
        try {
            PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream(pis);

            Sender sender = new Sender(pos);
            Reciever reciever = new Reciever(pis);

            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(sender);
            executorService.execute(reciever);

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Sender extends Thread {
        private PipedOutputStream pos = null;

        public Sender(PipedOutputStream pos) {
            this.pos = pos;
        }

        @Override
        public void run() {
            try {
                FileInputStream fis = new FileInputStream("d:\\input.txt");
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = fis.read(buf)) != -1) {
                    pos.write(buf, 0, len);
                }
                pos.flush();
                pos.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Reciever extends Thread {
        private PipedInputStream pis = null;

        public Reciever(PipedInputStream pis) {
            this.pis = pis;
        }

        @Override
        public void run() {
            try {
                FileOutputStream fos = new FileOutputStream("d:\\output.txt");
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = pis.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();
                fos.close();
                pis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}