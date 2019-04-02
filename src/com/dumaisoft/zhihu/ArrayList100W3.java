package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
* @Description TODO
* @Author alex
* @Date 2019/4/2 0002 下午 8:30
*/
public class ArrayList100W3 {
    private static int LIST_LENGTH = 1000000;
    //线程数量
    private static int THREAD_NUMBER = 10;
    //每个线程读取的list个数
    private static int SLICE_LENGTH = LIST_LENGTH / THREAD_NUMBER;

    static private class MyObject {
        int type;
        MyObject() {
            type = new Random().nextInt(100);
        }
        boolean judge(){
            //1号逻辑
//                        return type ==2;
            //2号逻辑
//             return Math.sin(type)<0.01;
            //3号逻辑
            float result = 0;
            for (int i = 0; i < 100; i++) {
                result += Math.sin(type * i);
            }
            return  result<0.1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<MyObject> myObjectArrayList = new ArrayList<>(LIST_LENGTH);
        for (int i = 0; i < LIST_LENGTH; i++) {
            myObjectArrayList.add(new MyObject());
        }

        //计算各种相关因子
        long WT = computeWT(myObjectArrayList);
        int n = Runtime.getRuntime().availableProcessors();
        long TOT = computeTOT(n);

        float singleThreadTime = WT;
        float multiThreadTime = TOT + WT / n;
        System.out.println("WT=" + WT + ",TOT=" + TOT + ",n=" + n);
        System.out.println("单线程执行时间预测：" + singleThreadTime);
        System.out.println("多线程执行时间预测：" + multiThreadTime);
        if (singleThreadTime < multiThreadTime) {
            System.out.println("single thread is good!");
        } else {
            System.out.println("multi thread is good!");
        }

        //1.直接遍历
        long start = System.currentTimeMillis();
        int numberEquals2 = 0;
        for (int i = 0; i < LIST_LENGTH; i++) {
            if (myObjectArrayList.get(i).judge()) {
                numberEquals2++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("线程数量1，线性遍历，花费的时间:" + (end - start) + " milliseconds, " + "符合条件的个数有:" + numberEquals2);

        //2.使用线程池
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
                        if (myObjectArrayList.get(j).judge()) {
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
        System.out.println("线程数量:" + THREAD_NUMBER + "(CPU核心)花费的时间:" + (end - start) + " milliseconds, " + "符合条件的个数有:" + numberEquals2);
    }

    private static long computeTOT(int n) {
        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    //为防止被优化，加两行行执行代码，一条指令即可完成
                    int j=0;
                    j++;
                }
            });
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    private static long computeWT(ArrayList<MyObject> myObjectArrayList) {
        long start = System.currentTimeMillis();
        int numberEquals = 0;
        for (int i = 0; i < LIST_LENGTH; i++) {
            if (myObjectArrayList.get(i).judge()) {
                numberEquals++;
            }
        }
        long end = System.currentTimeMillis();
        return end - start;
    }
}
