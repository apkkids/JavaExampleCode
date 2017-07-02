package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/23
 * Create Time: 16:27
 * Description:
 */
public class CountDownLatchExam {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.submit(new Horse("horse" + i, latch));
        }
        for (int i = 0; i < 3; i++) {
            service.submit(new Judge("judge" + i, latch));
        }
        service.shutdown();
    }


    private static class Horse implements Runnable {
        private final String name;
        private final CountDownLatch latch;

        Horse(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " is ready,wait for all judges.");
                latch.await();
                System.out.println(name + " is running.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Judge implements Runnable {
        private final String name;
        private final CountDownLatch latch;
        private static Random random = new Random(System.currentTimeMillis());

        Judge(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(name + " is ready.");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}