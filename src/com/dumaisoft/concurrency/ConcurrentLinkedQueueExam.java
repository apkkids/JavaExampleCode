package com.dumaisoft.concurrency;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/25
 * Create Time: 20:44
 * Description:
 */
public class ConcurrentLinkedQueueExam {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            service.submit(new Putter(linkedList));
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 2; i++) {
            service.submit(new Getter(linkedList));
        }
        service.shutdown();
    }
}

class Putter implements Runnable {
    private final LinkedList<Integer> linkedList;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    Putter(LinkedList<Integer> linkedList) {
        this.linkedList = linkedList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            linkedList.add(i);
            atomicInteger.incrementAndGet();
        }
        System.out.println("Putter=" + atomicInteger.get());
    }
}

class Getter implements Runnable {
    private final LinkedList<Integer> linkedList;
    private static AtomicInteger result = new AtomicInteger(0);

    Getter(LinkedList<Integer> linkedList) {
        this.linkedList = linkedList;
    }

    @Override
    public void run() {
        while (!linkedList.isEmpty()) {
            linkedList.poll();
            result.incrementAndGet();
            if (result.get() % 100 == 0) {
                System.out.println("Getter=" + result);
            }
        }
        System.out.println("Getter=" + result);
    }
}