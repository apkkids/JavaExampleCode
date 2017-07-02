package com.dumaisoft.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/6
 * Create Time: 21:54
 * Description:
 */
public class AtomicFloatExam {
    public static void main(String[] args) throws InterruptedException {
        float f = 3.14f;
        final AtomicInteger atomicInteger = new AtomicInteger(Float.floatToIntBits(f));
        System.out.println(atomicInteger.get());
        System.out.println(Float.intBitsToFloat(atomicInteger.get()));

        ExecutorService service = Executors.newCachedThreadPool();
        for (int j = 0; j < 10; j++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 10000; k++) {
                        atomicInteger.addAndGet(Float.floatToIntBits(3.14f));
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(Float.intBitsToFloat(atomicInteger.get()));
    }
}
