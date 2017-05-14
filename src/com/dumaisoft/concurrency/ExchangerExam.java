package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/24
 * Create Time: 21:28
 * Description:
 */
public class ExchangerExam {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new StringHolder("LeftHand", "LeftValue", exchanger));
        service.submit(new StringHolder("RightHand", "RightValue", exchanger));
        service.shutdown();
    }
}

class StringHolder implements Runnable {
    private final String name;
    private final String val;
    private final Exchanger<String> exchanger;
    private static Random rand = new Random(47);

    StringHolder(String name, String val, Exchanger<String> exchanger) {
        this.name = name;
        this.val = val;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            System.out.println(name +" hold the val:"+val);
            TimeUnit.SECONDS.sleep(rand.nextInt(5));
            String str = exchanger.exchange(val);
            System.out.println(name+" get the val:"+str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
