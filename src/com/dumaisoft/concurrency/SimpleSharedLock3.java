package com.dumaisoft.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/22
 * Create Time: 22:30
 * Description:
 */
public class SimpleSharedLock3 {
    private static class Sync extends AbstractQueuedSynchronizer {
        final int getPermits() {
            return getState();
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {
                int available = getState();
                int remaining = available - arg;
                if (remaining < 0 ||
                        compareAndSetState(available, remaining))
                    return remaining;
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int available = getState();
                int remaining = available + arg;
                if (compareAndSetState(available, remaining)) {
                    return true;
                }
            }
        }

        protected Sync(int permits) {
            setState(permits);
        }
    }

    public SimpleSharedLock3(int permits) {
        sync = new Sync(permits);
    }

    private final Sync sync;

    public void lock() {
        sync.acquireShared(1);
    }

    public void unlock() {
        sync.releaseShared(1);
    }

    public int getPermits() {
        return sync.getPermits();
    }

    private static class MyThread extends Thread {
        private final String name;
        private final SimpleSharedLock3 lock;

        private MyThread(String name, SimpleSharedLock3 lock) {
            this.name = name;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(name + " get the lock, permits = " + lock.getPermits());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //给予此锁2个许可
        final SimpleSharedLock3 lock = new SimpleSharedLock3(2);
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        MyThread t3 = new MyThread("t3", lock);
        t1.start();
        t2.start();
        t3.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            lock.unlock();
            System.out.println("main thread invoke unlock, permits = " + lock.getPermits());
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread exit!");
    }
}