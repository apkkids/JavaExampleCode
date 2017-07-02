package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/1
 * Create Time: 21:21
 * Description:
 */
public class ExchangerExam2 {
    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService service = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        service.submit(new StringHolder("LeftHand", "LeftValue", exchanger));
        service.submit(new StringHolder("RightHand", "RightValue", exchanger));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        long end = System.currentTimeMillis();
        System.out.println("time span is " + (end - start) + " milliseconds");
    }


    private static class StringHolder implements Runnable {
        private final String name;
        private final String val;
        private final Exchanger<String> exchanger;
        private static Random rand = new Random(System.currentTimeMillis());

        StringHolder(String name, String val, Exchanger<String> exchanger) {
            this.name = name;
            this.val = val;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10000; i++) {
//                    System.out.println(name + "-" + i + ": hold the val:" + val + i);
//                    TimeUnit.NANOSECONDS.sleep(rand.nextInt(5));
                    String str = exchanger.exchange(val + i);
//                    System.out.println(name + "-" + i + ": get the val:" + str);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
