package com.dumaisoft.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/22
 * Create Time: 11:33
 * Description:
 */
public class SemaphoreExam {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService service = Executors.newCachedThreadPool();
        // 10 cars wait for 3 semaphore
        for (int i = 0; i < 10; i++) {
            service.submit(new Car("Car" + i, semaphore));
        }

        service.shutdown();
    }
}

class Car implements Runnable {
    private final String name;
    private final Semaphore semaphore;
    private static Random random = new Random(43);

    Car(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is waiting for a permit");
            semaphore.acquire();
            System.out.println(name+" get a permit to access");
            TimeUnit.SECONDS.sleep(random.nextInt(5));
            System.out.println(name + " release a permit to available");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}