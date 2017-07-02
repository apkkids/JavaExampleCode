package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/24
 * Create Time: 21:15
 * Description:
 */
public class ReentrantReadWriteLockExam {
    public static Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        StringBuilder blackboard = new StringBuilder(100);

        ExecutorService service= Executors.newCachedThreadPool();
        service.execute(new Teacher(lock, "Chinese Teacher", blackboard));
        service.execute(new Teacher(lock, "English Teacher", blackboard));
        service.execute(new Student(lock,"Wang",blackboard));
        service.execute(new Student(lock,"Li",blackboard));
        service.execute(new Student(lock,"Zhang",blackboard));
        service.shutdown();
    }

    private static class Teacher extends Thread {
        final private ReentrantReadWriteLock lock;
        final private String name;
        final private StringBuilder blackboard;
        private AtomicInteger id = new AtomicInteger(0);

        private Teacher(ReentrantReadWriteLock lock, String name, StringBuilder blackboard) {
            this.lock = lock;
            this.name = name;
            this.blackboard = blackboard;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                try {
                    lock.writeLock().lock();
                    System.out.println(name + " get write lock");
                    blackboard.delete(0, 99).append(name + " has written on the blackboard, number = " + id.getAndIncrement());
                    System.out.println(name + " write content:" + blackboard);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                    System.out.println(name + " release write lock");
                }
            }
        }
    }

    private static class Student extends Thread {
        final private ReentrantReadWriteLock lock;
        final private String name;
        final private StringBuilder blackboard;

        private Student(ReentrantReadWriteLock lock, String name, StringBuilder blackboard) {
            this.lock = lock;
            this.name = name;
            this.blackboard = blackboard;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                try {
                    lock.readLock().lock();
                    System.out.println(name + " get read lock");
                    System.out.println(name + " read the blackboard:" + blackboard);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.readLock().unlock();
                    System.out.println(name+" release the read lock");
                }
            }
        }
    }
}
