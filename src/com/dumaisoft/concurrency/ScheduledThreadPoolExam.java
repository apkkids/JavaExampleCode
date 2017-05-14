package com.dumaisoft.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/5/2
 * Create Time: 22:24
 * Description:
 */
public class ScheduledThreadPoolExam {
    public static void main(String[] args) {
        //first test for singleThreadScheduledPool
        ScheduledExecutorService scheduledPool = Executors.newSingleThreadScheduledExecutor();
        //second test for scheduledThreadPool
//        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 5; i++) {
            scheduledPool.schedule(new TaskInScheduledPool(i), 0, TimeUnit.SECONDS);
        }
        scheduledPool.shutdown();
    }
}

class TaskInScheduledPool implements Runnable {
    private final int id;

    TaskInScheduledPool(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("TaskInScheduledPool-["+id+"] is running phase-"+i);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("TaskInScheduledPool-["+id+"] is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}