package com.dumaisoft.zhihu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxb on 2017/8/29 0029.
 * 1毫秒=1ms=1/1000秒，表达方式TimeUnit.MILLISECONDS
 * 1微秒=1μs=1/1000毫秒，表达方式TimeUnit.MICROSECONDS
 * 1纳秒=1ns=1/1000微秒，表达方式TimeUnit.NANOSECONDS
 */
public class TimeTest {
    static final long PER_NANO = 1000 * 1000;  //每毫秒为1000*1000纳秒

    public static void main(String[] args) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final long times[] = new long[100];  //用来存放时间的数组
        Worker worker = new Worker(times);
        //重复任务，延迟100ms开始，间隔100ms，全部使用NANOSECONDS作为单位
        final ScheduledFuture<?> beeperHandle =
                scheduler.scheduleAtFixedRate(worker, 100 * PER_NANO, 100 * PER_NANO, TimeUnit.NANOSECONDS);
//        final ScheduledFuture<?> beeperHandle =
//                scheduler.scheduleAtFixedRate(worker, 100, 100, TimeUnit.MILLISECONDS);
        //延迟任务，延迟10000ms执行。取消重复任务，输出结果
        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
                long max = 0;
                for (int i = 1; i < times.length; i++) {
                    long diff = times[i] - times[i - 1];
                    max = Math.max(max, diff);
                    System.out.println(diff);
                }
                double maxdiff = ((double) (max) / PER_NANO) - 100;
                System.out.println("max = " + maxdiff + " ms");
                scheduler.shutdown();
            }
        }, 10000 * PER_NANO, TimeUnit.NANOSECONDS);
    }

    //为了防止IO占用时间，把时间先存入数组，最后再一次输出
    static class Worker implements Runnable {
        private static int number;   //序号
        private final long times[]; //时间数组

        Worker(long times[]) {
            this.times = times;
        }

        @Override
        public void run() {
            times[number++] = System.nanoTime();
        }
    }
}
