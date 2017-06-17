package com.dumaisoft.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/17
 * Create Time: 11:04
 * Description:
 */
public class LockSupportBlockerExam {
    public static void main(String[] args) {
        Thread t3 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("t3");
                System.out.println(Thread.currentThread().getName() + " park 5 seconds");
                //park 5 seconds, set blocker
                Object blocker = new String("Alex Wang");
                LockSupport.parkUntil(blocker, System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS));
                System.out.println(Thread.currentThread().getName() + " after park");
            }
        };
        t3.start();

        try {
            Object t3_blocker = null;
            while (t3_blocker == null) {
                t3_blocker = LockSupport.getBlocker(t3);
                TimeUnit.MILLISECONDS.sleep(10);
            }
            System.out.println("t3 blocker is :" + t3_blocker);
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
