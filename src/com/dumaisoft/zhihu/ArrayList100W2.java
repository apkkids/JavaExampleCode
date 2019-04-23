package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2019/3/12
 * Create Time: 8:28
 * Description:
 */
public class ArrayList100W2 {
    private static int LIST_LENGTH = 1000000;
    //线程数量
    private static int THREAD_NUMBER = 10;
    //每个线程读取的list个数
    private static int SLICE_LENGTH = LIST_LENGTH / THREAD_NUMBER;

    public static void main(String[] args) throws InterruptedException {
        ArrayList<MyObject> myObjectArrayList = new ArrayList<>(LIST_LENGTH);
        for (int i = 0; i < LIST_LENGTH; i++) {
            myObjectArrayList.add(new MyObject());
        }
        //第一种方法，直接遍历
        long start = System.currentTimeMillis();
        int numberEquals2 = 0;
        for (int i = 0; i < LIST_LENGTH; i++) {
            if (myObjectArrayList.get(i).type == 2) {
                numberEquals2++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("线程数量1，线性遍历，花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + numberEquals2);

        //第二种方法，用100个线程来分别跑，用来计数的变量是原子变量
        start = System.currentTimeMillis();
        AtomicInteger atomicNumberEquals2 = new AtomicInteger(0);
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            final int threadNumber = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = threadNumber * SLICE_LENGTH; j < ((threadNumber + 1) * SLICE_LENGTH); j++) {
                        if (myObjectArrayList.get(j).type == 2) {
                            atomicNumberEquals2.addAndGet(1);
                        }
                    }
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        end = System.currentTimeMillis();
        System.out.println("线程数量:" + THREAD_NUMBER + "(原子变量)花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + atomicNumberEquals2.get());

        //第三种方法，用100个线程来分别跑，使用数组来计数
        start = System.currentTimeMillis();
        int result[] = new int[THREAD_NUMBER];
        ExecutorService pool2 = Executors.newFixedThreadPool(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            final int threadNumber = i;
            pool2.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = threadNumber * SLICE_LENGTH; j < ((threadNumber + 1) * SLICE_LENGTH); j++) {
                        if (myObjectArrayList.get(j).type == 2) {
                            result[threadNumber]++;
                        }
                    }
                }
            });
        }
        pool2.shutdown();
        pool2.awaitTermination(1, TimeUnit.DAYS);
        numberEquals2 = 0;
        for (int i = 0; i < THREAD_NUMBER; i++) {
            numberEquals2 += result[i];
        }
        end = System.currentTimeMillis();
        System.out.println("线程数量:" + THREAD_NUMBER + "(数组存储)花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + numberEquals2);

        //第四种方法，获取本机CPU核心数，设置为线程数量
        THREAD_NUMBER = Runtime.getRuntime().availableProcessors();
        SLICE_LENGTH = LIST_LENGTH / THREAD_NUMBER;
        start = System.currentTimeMillis();
        int[] result2 = new int[THREAD_NUMBER];
        ExecutorService pool3 = Executors.newFixedThreadPool(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            final int threadNumber = i;
            pool3.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = threadNumber * SLICE_LENGTH; j < ((threadNumber + 1) * SLICE_LENGTH); j++) {
                        if (myObjectArrayList.get(j).type == 2) {
                            result2[threadNumber]++;
                        }
                    }
                }
            });
        }
        pool3.shutdown();
        pool3.awaitTermination(1, TimeUnit.DAYS);
        numberEquals2 = 0;
        for (int i = 0; i < THREAD_NUMBER; i++) {
            numberEquals2 += result2[i];
        }
        end = System.currentTimeMillis();
        System.out.println("线程数量:" + THREAD_NUMBER + "(CPU核心)花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + numberEquals2);


        //第五种方法，使用parallelStream
        AtomicInteger atomicResult = new AtomicInteger(0);
        start = System.currentTimeMillis();
        myObjectArrayList.parallelStream().forEach( element ->{
            if (element.type ==2){
                atomicResult.addAndGet(1);
            }
        });
        end = System.currentTimeMillis();
        System.out.println("线程数量(默认为CPU核心数):, 花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + atomicResult.get());

        //第六种方法，使用filter
        start = System.currentTimeMillis();
        long totalCount = myObjectArrayList.parallelStream().filter(element-> element.judge()).count();
        end = System.currentTimeMillis();
        System.out.println("filter方法(默认为CPU核心数):, 花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + totalCount);
    }
}

class MyObject {
    int type;

    MyObject() {
        type = new Random().nextInt(100);
    }

    boolean judge(){
        return type ==2;
    }
}
