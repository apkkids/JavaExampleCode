package com.dumaisoft.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/6
 * Create Time: 21:57
 * Description:
 */
public class PhaserTermination {
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(1){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("Termination state");
                return super.onAdvance(phase, registeredParties);
            }
        };
        ExecutorService service= Executors.newCachedThreadPool();
        service.execute(new NeverEnd(phaser));
        service.execute(new NeverEnd(phaser));
        service.execute(new NeverEnd(phaser));
        service.shutdown();
        System.out.println("main thread wait 5 seconds");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("main thread terminate the phaser");
        phaser.forceTermination();
    }

    private static class NeverEnd implements Runnable {
        final Phaser phaser;

        private NeverEnd(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
                phaser.register();
                System.out.println(Thread.currentThread()+" is running , it's never end.");
                System.out.println(Thread.currentThread()+" is end, return value = "+ phaser.arriveAndAwaitAdvance());
        }
    }
}
