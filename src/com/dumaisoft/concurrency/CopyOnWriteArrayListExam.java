package com.dumaisoft.concurrency;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wxb on 2017/9/29 0029.
 */
public class CopyOnWriteArrayListExam {
    private static final int TEST_NUM = 200000;
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < TEST_NUM; i++) {
            list.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("time span = "+(end-start));

        list.clear();
        start = System.currentTimeMillis();
        Integer[] integers = new Integer[TEST_NUM];
        for (int i = 0; i < TEST_NUM; i++) {
            integers[i]=i;
        }
        list = new CopyOnWriteArrayList<>(integers);
        end = System.currentTimeMillis();
        System.out.println("time span = "+(end-start));
    }
}
