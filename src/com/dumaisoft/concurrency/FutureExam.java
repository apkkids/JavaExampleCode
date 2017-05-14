package com.dumaisoft.concurrency;

import java.util.concurrent.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/21
 * Create Time: 21:33
 * Description:
 */
public class FutureExam {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new TaskWithResult(), "result");
        service.shutdown();
        System.out.println("future.get() = " + future.get());
    }
}

class TaskWithResult implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getClass().getSimpleName());
    }
}