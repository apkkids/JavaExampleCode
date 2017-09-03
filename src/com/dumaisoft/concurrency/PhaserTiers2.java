package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/8
 * Create Time: 14:10
 * Description:
 */

public class PhaserTiers2 {
    final static int TASKS_PER_PHASER = 4;

    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser("rootPhaser");

        Task[] tasks = new Task[12];
        build(tasks, 0, 12, phaser);
    }

    private static class MyPhaser extends Phaser {
        final private String name;

        public String getName() {
            return name;
        }

        public MyPhaser(String name) {
            this.name = name;
        }

        public MyPhaser(Phaser ph, String name) {
            super(ph);
            this.name = name;
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            System.out.println("================== all threads is arrived ===================");
            return super.onAdvance(phase, registeredParties);
        }
    }

    static void build(Task[] tasks, int lo, int hi, MyPhaser ph) {
        if (hi - lo > TASKS_PER_PHASER) {
            for (int i = lo; i < hi; i += TASKS_PER_PHASER) {
                int j = Math.min(i + TASKS_PER_PHASER, hi);
                build(tasks, i, j, new MyPhaser(ph, "SonPhaser" + i / TASKS_PER_PHASER));
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
        final MyPhaser phaser;

        private Task(MyPhaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            phaser.register();
            System.out.println(Thread.currentThread() + " is working, it has registered in " + phaser.getName());
            try {
                TimeUnit.SECONDS.sleep(rand.nextInt(5)+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}