package com.dumaisoft.concurrency;

import java.util.concurrent.ForkJoinPool;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/5/3
 * Create Time: 22:37
 * Description:
 */
public class RecursiveSequenceExam {
    private final static int NUMBER = 100000000;

    public static void main(String[] args) {
        double[] array = new double[NUMBER];
        for (int i = 0; i < NUMBER; i++) {
            array[i] = i;
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER; i++) {
            array[i] = Math.sin(array[i]) + Math.cos(array[i]) + Math.tan(array[i]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time span = " + (endTime - startTime));
    }
}
