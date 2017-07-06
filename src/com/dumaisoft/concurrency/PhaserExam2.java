package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/6
 * Create Time: 21:37
 * Description:
 */
public class PhaserExam2 {
    public static void main(String[] args) {
        //循环的次数
        final int iterations = 3;
        Phaser phaser = new Phaser(){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                //当phase number大于等于指定值，或者注册的parties数量等于0时，Phaser终止
                System.out.println("============== all threads arrive at phase :"+getPhase()+" ==============");
                return phase >= (iterations - 1) || registeredParties == 0;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new CyclicTask(phaser));
        service.execute(new CyclicTask(phaser));
        service.execute(new CyclicTask(phaser));
        service.shutdown();
    }

    private static class CyclicTask implements Runnable {
        static Random rand = new Random(System.currentTimeMillis());
        final Phaser phaser;
        private CyclicTask(Phaser phaser) {
            this.phaser = phaser;
        }
        @Override
        public void run() {
            phaser.register();
            try {
                do {
                    System.out.println(Thread.currentThread() + " is begin, doing some work.");
                    TimeUnit.SECONDS.sleep(rand.nextInt(5));
                    phaser.arriveAndAwaitAdvance();
                    System.out.println(Thread.currentThread() + " is over.");
                } while (!phaser.isTerminated());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
