package com.dumaisoft.concurrency;

import java.util.concurrent.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/21
 * Create Time: 22:15
 * Description:
 */
public class CallableExam {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Callable is running");
                TimeUnit.SECONDS.sleep(2);
                return 47;
            }
        });
        service.shutdown();
        System.out.println("future.get = " + future.get());
    }
}
