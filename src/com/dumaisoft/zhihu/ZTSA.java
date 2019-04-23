package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description ZTSA算法
 * @Author alex
 * @Date 2019/4/2 0002 下午 7:57
 */
public class ZTSA {
    private static int LIST_LENGTH = 1000000;

    static private class MyObject {
        int type;
        MyObject() {
            type = new Random().nextInt(100);
        }
        // 业务逻辑写在此方法中
        boolean judge() {
            return type == 2;
        }
    }

    public static void main(String[] args) {
        ArrayList<MyObject> myObjectArrayList = new ArrayList<>(LIST_LENGTH);
        for (int i = 0; i < LIST_LENGTH; i++) {
            myObjectArrayList.add(new MyObject());
        }
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
    }

    private static long computeTOT(int n) {
        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    //为防止被优化，加两行行执行代码，一条指令即可完成
                    int j = 0;
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
