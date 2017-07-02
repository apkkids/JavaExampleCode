package com.dumaisoft.concurrency;

import java.util.Timer;
import java.util.concurrent.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/21
 * Create Time: 22:45
 * Description: Using ScheduledExecutorService to execute fixed rate task.
 *              and using it to schedule a task to be executed at a later time.
 */
public class ScheduledExecutorServiceExam {
    public static void main(String[] args) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        final ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("beep");
            }
        }, 1, 1, TimeUnit.SECONDS);

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("cancel beep");
                scheduledFuture.cancel(true);
                scheduler.shutdown();
            }
        }, 10, TimeUnit.SECONDS);
        System.currentTimeMillis();
    }
}
