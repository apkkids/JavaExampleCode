package com.dumaisoft.concurrency;

import java.util.concurrent.*;

/**
 * Created by wxb on 2017/10/20 0020.
 */
public class FutureTaskExam {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                return "Alex";
            }
        });
        service.submit(futureTask);
        service.shutdown();
        System.out.println("waiting for futureTask.get() = "+futureTask.get());
    }
}
