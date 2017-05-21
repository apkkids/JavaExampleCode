package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/5/21
 * Create Time: 22:05
 * Description:
 */
public class TransferQueueExam2 {
    public static void main(String[] args) {
        TransferQueue<String> queue = new LinkedTransferQueue<>();
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new Producer("Producer1", queue));
        service.submit(new Producer("Producer2", queue));
        service.submit(new Consumer("Consumer1", queue));
        service.submit(new Consumer("Consumer2", queue));
        service.shutdown();
    }

    static class Producer implements Runnable {
        private final String name;
        private final TransferQueue<String> queue;

        Producer(String name, TransferQueue<String> queue) {
            this.name = name;
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println(name + " begin transfer objects");

            try {
                for (int i = 0; i < 5; i++) {
                    queue.transfer(name + "_Product" + i);
                    System.out.println(name + " transfer " + name + "_Product" + i);
                }
                System.out.println(name + " after transformation");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " is over");
        }
    }

    static class Consumer implements Runnable {
        private final String name;
        private final TransferQueue<String> queue;
        private static Random rand = new Random(System.currentTimeMillis());

        Consumer(String name, TransferQueue<String> queue) {
            this.name = name;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    String str = queue.take();
                    System.out.println(name + " take " + str);
                    TimeUnit.SECONDS.sleep(rand.nextInt(5));
                }
                System.out.println(name + " is over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
