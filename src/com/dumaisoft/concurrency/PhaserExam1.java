package com.dumaisoft.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/3
 * Create Time: 21:57
 * Description: use Phaser as a CountdownLatch
 */
public class PhaserExam1 {
    public static void main(String[] args) {
        //初始化时parties设置为1
        Phaser phaser = new Phaser(1);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new MyTask(phaser));
        service.execute(new MyTask(phaser));
        service.execute(new MyTask(phaser));
        service.shutdown();
        try {
            System.out.println("main thread sleep for 5 seconds.");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("In main thread, registered parties:" + phaser.getRegisteredParties());
            System.out.println("In main thread, arrived parties:" + phaser.getArrivedParties());
            //到达并deregister，此时parties会减少至3，从而释放所有线程
            phaser.arriveAndDeregister();
            System.out.println("main thread releases all waiting threads.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class MyTask implements Runnable {
        final Phaser phaser;

        private MyTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            //每个线程register，意味着parties加1
            phaser.register();
            System.out.println(Thread.currentThread() + " is waiting for synchronization, registered parties:" + phaser.getRegisteredParties());
            //等待所有parties到达
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread() + " is arrived, arrived parties:" + phaser.getArrivedParties());
        }
    }
}
