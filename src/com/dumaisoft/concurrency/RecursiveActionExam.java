package com.dumaisoft.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/5/2
 * Create Time: 22:39
 * Description:
 */
public class RecursiveActionExam {
    private final static int NUMBER = 100000000;

    public static void main(String[] args) {
        double[] array = new double[NUMBER];
        for (int i = 0; i < NUMBER; i++) {
            array[i] = i;
        }
        long startTime = System.currentTimeMillis();
        System.out.println(Runtime.getRuntime().availableProcessors());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ComputeTask(array, 0, array.length));
        long endTime = System.currentTimeMillis();
        System.out.println("Time span = " + (endTime - startTime));
    }
}

class ComputeTask extends RecursiveAction {
    final double[] array;
    final int lo, hi;

    ComputeTask(double[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    protected void compute() {
        if (hi - lo < 2) {
            for (int i = lo; i < hi; ++i)
                array[i] = Math.sin(array[i]) + Math.cos(array[i]) + Math.tan(array[i]);
        } else {
            int mid = (lo + hi) >>> 1;
            invokeAll(new ComputeTask(array, lo, mid),
                    new ComputeTask(array, mid, hi));
        }
    }
}