package com.dumaisoft.zhihu;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/11
 * Create Time: 22:02
 * Description:
 */
public class TestNullSynchronize {
    public static void main(String[] args) {
        final Object obj = null;
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(Thread.currentThread()+": i");
                    }
                }
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(Thread.currentThread()+": i");
                    }
                }
            }
        });
        service.shutdown();
    }
}
