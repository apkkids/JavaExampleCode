package com.dumaisoft.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/20
 * Create Time: 22:15
 * Description:
 */
public class CountDownDebug {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        Thread t1 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("t1");
                try {
                    //t1阻塞在此处，假设它在队列中是第一个（在head之后的那个线程），那么它将被第三个countDown释放；
                    //然后在doAcquireSharedInterruptibly方法中进入setHeadAndPropagate(node, r)方法
                    //然后调用doReleaseShared方法并行化的释放后续共享阻塞的线程（也可能是t2再释放t3）
                    latch.await();
                    System.out.println(Thread.currentThread() + " get latch");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("t2");
                try {
                    latch.await();
                    System.out.println(Thread.currentThread() + " get latch");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t3 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("t3");
                try {
                    latch.await();
                    System.out.println(Thread.currentThread() + " get latch");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        t2.start();
        t3.start();
        latch.countDown();
        latch.countDown();
        latch.countDown();
        try {
            t1.join();
            t2.join();
            t3.join();
            System.out.println("main thread is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
