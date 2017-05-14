package com.dumaisoft.concurrency;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/24
 * Create Time: 21:34
 * Description:
 */
public class BlockingQueueExam {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(3);
//        BlockingQueue<String> queue =  new ArrayBlockingQueue<>(3, true);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.submit(new Producer("Producer" + i, blockingQueue));
        }
        for (int i = 0; i < 5; i++) {
            service.submit(new Consumer("Consumer" + i, blockingQueue));
        }
        service.shutdown();
    }
}

class Producer implements Runnable {
    private final String name;
    private final BlockingQueue<String> blockingQueue;
    private static Random rand = new Random(47);
    private static AtomicInteger productID = new AtomicInteger(0);

    Producer(String name, BlockingQueue<String> blockingQueue) {
        this.name = name;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                SECONDS.sleep(rand.nextInt(5));
                String str = "Product" + productID.getAndIncrement();
                blockingQueue.add(str);
                //注意，这里得到的size()有可能是错误的
                System.out.println(name + " product " + str + ", queue size = " + blockingQueue.size());
            }
            System.out.println(name + " is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private final String name;
    private final BlockingQueue<String> blockingQueue;
    private static Random rand = new Random(47);

    Consumer(String name, BlockingQueue<String> blockingQueue) {
        this.name = name;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                SECONDS.sleep(rand.nextInt(5));
                String str = blockingQueue.take();
                //注意，这里得到的size()有可能是错误的
                System.out.println(name + " consume " + str + ", queue size = " + blockingQueue.size());
            }
            System.out.println(name + " is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}