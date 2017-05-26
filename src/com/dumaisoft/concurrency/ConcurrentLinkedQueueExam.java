package com.dumaisoft.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/25
 * Create Time: 20:44
 * Description:
 */
public class ConcurrentLinkedQueueExam {
    private static final int TEST_INT = 10000000;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Queue<Integer> queue = null;
        if (args.length < 1) {
            System.out.println("Usage: input 1~4 ");
            System.exit(1);
        }
        int param = Integer.parseInt(args[0]);
        switch (param) {
            case 1:
                queue = new LinkedList<>();
                break;
            case 2:
                queue = new LinkedBlockingQueue<>();
                break;
            case 3:
                queue = new ArrayBlockingQueue<Integer>(TEST_INT * 5);
                break;
            case 4:
                queue = new ConcurrentLinkedQueue<>();
                break;
            default:
                System.out.println("Usage: input 1~4 ");
                System.exit(2);
        }
        System.out.println("Using " + queue.getClass().getSimpleName());

        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.submit(new Putter(queue, "Putter" + i));
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 5; i++) {
            service.submit(new Getter(queue, "Getter" + i));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        long end = System.currentTimeMillis();
        System.out.println("Time span = " + (end - start));
        System.out.println("queue size = " + queue.size());
    }

    static class Putter implements Runnable {
        private final Queue<Integer> queue;
        private final String name;

        Putter(Queue<Integer> queue, String name) {
            this.queue = queue;
            this.name = name;
        }


        @Override
        public void run() {
            for (int i = 0; i < TEST_INT; i++) {
                queue.offer(1);
            }
            System.out.println(name + " is over");
        }
    }

    static class Getter implements Runnable {
        private final Queue<Integer> queue;
        private final String name;

        Getter(Queue<Integer> queue, String name) {
            this.queue = queue;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < TEST_INT; i++) {
                synchronized (Getter.class) {
                    if (!queue.isEmpty()) {
                        queue.poll();
                    }
                }
            }
            System.out.println(name + " is over");
        }
    }
}

