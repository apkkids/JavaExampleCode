package com.dumaisoft.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/12
 * Create Time: 23:01
 * Description:
 */
public class PrimitiveAtomTest {
    private static final long COMPUTE_TIMES = 1000000L;
    private static int i = 0;
    private static volatile int vi = 0;
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (long j = 0; j < COMPUTE_TIMES; j++) {
                    atomicInteger.incrementAndGet();
                    vi = vi + 1;
                    i = i + 1;
                }

            }
        };
        t1.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (long j = 0; j < COMPUTE_TIMES; j++) {
                    atomicInteger.decrementAndGet();
                    vi = vi - 1;
                    i = i - 1;
                }
            }
        };
        t2.start();

        try {
            t2.join();
            t1.join();

            System.out.println("int i = " + i);
            System.out.println("volatile vi = " + vi);
            System.out.println("atomicInteger=" + atomicInteger.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
