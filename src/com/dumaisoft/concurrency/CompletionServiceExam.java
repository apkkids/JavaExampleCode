package com.dumaisoft.concurrency;

import java.util.concurrent.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/4/21
 * Create Time: 22:18
 * Description: CompletionService中提交的任务，会按照完成时间的先后入队，take()则按照入队顺序取出结果，没有结果时会阻塞
 */
public class CompletionServiceExam {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newCachedThreadPool();
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(service);
        for (int i = 0; i < 5; i++) {
            completionService.submit(new TaskInteger(i));
        }
        service.shutdown();
        //will block
        for (int i = 0; i < 5; i++) {
            Future<Integer> future = completionService.take();
            System.out.println(future.get());
        }

    }
}

class TaskInteger implements Callable<Integer> {
    private final int sum;

    TaskInteger(int sum)  {
        this.sum = sum;
    }

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(sum);
        return sum * sum;
    }
}