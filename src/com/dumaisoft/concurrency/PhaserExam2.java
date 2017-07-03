package com.dumaisoft.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/3
 * Create Time: 22:11
 * Description: use Phaser as CyclicBarrier
 */
public class PhaserExam2 {
    public static void main(String[] args) {
        List<Runnable> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new CyclicTask());
        }
        //循环3次
        startTasks(list, 3);
    }

    static void startTasks(List<Runnable> tasks, final int iterations) {
        final Phaser phaser = new Phaser() {
            protected boolean onAdvance(int phase, int registeredParties) {
                //当phase number大于等于指定值，或者注册的parties数量等于0时，Phaser终止
                return phase >= (iterations - 1) || registeredParties == 0;
            }
        };
        phaser.register();
        for (final Runnable task : tasks) {
            phaser.register();
            new Thread() {
                public void run() {
                    do {
                        task.run();
                        phaser.arriveAndAwaitAdvance();
                    } while (!phaser.isTerminated());  //检测终止条件
                }
            }.start();
        }
        phaser.arriveAndDeregister(); // deregister self, don't wait
    }

    private static class CyclicTask implements Runnable {
        static Random rand = new Random(System.currentTimeMillis());

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread() + " is begin.");
                TimeUnit.SECONDS.sleep(rand.nextInt(5));
                System.out.println(Thread.currentThread() + " is over.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
