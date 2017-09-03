package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/7
 * Create Time: 21:51
 * Description:
 */
public class PhaserTiers {
    final static int TASKS_PER_PHASER = 4;

    public static void main(String[] args) {
        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("================== all threads is arrived ===================");
                return super.onAdvance(phase, registeredParties);
            }
        };
        Task[] tasks = new Task[12];
        build(tasks, 0, 12, phaser);
    }

    static void build(Task[] tasks, int lo, int hi, Phaser ph) {
        if (hi - lo > TASKS_PER_PHASER) {
            for (int i = lo; i < hi; i += TASKS_PER_PHASER) {
                int j = Math.min(i + TASKS_PER_PHASER, hi);
                build(tasks, i, j, new Phaser(ph));
            }
        } else {
            for (int i = lo; i < hi; ++i) {
                tasks[i] = new Task(ph);
                tasks[i].start();
            }
        }
    }

    private static class Task extends Thread {
        private static Random rand = new Random(System.currentTimeMillis());
        final Phaser phaser;

        private Task(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            phaser.register();
            System.out.println(Thread.currentThread() + " is working.");
            try {
                TimeUnit.SECONDS.sleep(rand.nextInt(5) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
