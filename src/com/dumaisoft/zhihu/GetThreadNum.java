package com.dumaisoft.zhihu;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/4
 * Create Time: 21:54
 * Description:
 */
public class GetThreadNum {
    public static void main(String[] args) throws InterruptedException {

        //创建10个子线程
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.currentThread().setName("Alex-Wang-"+ finalI);
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        TimeUnit.SECONDS.sleep(1);
        //获取本线程所在线程组的所有线程数量
        int n = Thread.activeCount();
        System.out.println("Thread number in same ThreadGroup:"+n);

        //获取本进程中所有线程数量
        System.out.println("Thread number:"+ManagementFactory.getThreadMXBean().getThreadCount());

        //获取本进程中所有线程，并得到线程的相关信息
        Map<Thread, StackTraceElement[]> maps = Thread.getAllStackTraces();
        System.out.println("Thread number:"+maps.size());
        System.out.println("Thread information:");
        for (Map.Entry<Thread, StackTraceElement[]> entry:maps.entrySet()
             ) {
            System.out.println(entry.getKey());
        }
    }
}
