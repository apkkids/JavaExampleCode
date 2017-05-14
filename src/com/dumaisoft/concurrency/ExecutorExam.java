package com.dumaisoft.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/21
 * Create Time: 21:25
 * Description:
 */
public class ExecutorExam {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Task("task1"));
        service.execute(new Task("task2"));
        service.execute(new Task("task3"));
        service.shutdown();
    }
}

class Task implements Runnable {
    private final String name;

    Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "-[" + i + "]");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}