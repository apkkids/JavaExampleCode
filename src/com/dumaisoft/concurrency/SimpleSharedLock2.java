package com.dumaisoft.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/22
 * Create Time: 22:21
 * Description:
 */
public class SimpleSharedLock2 {
    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                if (compareAndSetState(1, 0)) {
                    return true;
                }
            }
        }

        protected Sync() {
            setState(1);
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquireShared(1);
    }

    public void unlock() {
        sync.releaseShared(1);
    }

    private static class MyThread extends Thread {
        private final String name;
        private final SimpleSharedLock2 lock;

        private MyThread(String name, SimpleSharedLock2 lock) {
            this.name = name;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " begin to get lock");
                lock.lock();
                System.out.println(name + " get the lock");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final SimpleSharedLock2 lock = new SimpleSharedLock2();
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        MyThread t3 = new MyThread("t3", lock);
        t1.start();
        t2.start();
        t3.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("main thread invoke unlock ");
            lock.unlock();
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread exit!");
    }
}