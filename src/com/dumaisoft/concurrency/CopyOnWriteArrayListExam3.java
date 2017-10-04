package com.dumaisoft.concurrency;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxb on 2017/10/2 0002.
 */
public class CopyOnWriteArrayListExam3 {
    private static final int TEST_NUM = 100000;
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Integer[] integers = new Integer[TEST_NUM];
        for (int i = 0; i < TEST_NUM; i++) {
            integers[i] = i;
        }
//        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
        list.addAll(Arrays.asList(integers));
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 90; i++) {
            service.execute(new Getter(list));
        }
        for (int i = 0; i < 10; i++) {
            service.execute(new Putter(list));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        long end = System.currentTimeMillis();
        System.out.println("time span = " + (end - start));
        System.out.println(list.size());
    }

    private static class Getter implements Runnable {
        final List<Integer> list ;
        private static Random rand = new Random(System.currentTimeMillis());
        private Getter(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (int i = 0; i < TEST_NUM; i++) {
                list.get(rand.nextInt(TEST_NUM));
            }
        }
    }

    private static class Putter implements Runnable {
        final List<Integer> list ;
        private static Random rand = new Random(System.currentTimeMillis());
        private Putter(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (int i = 0; i < TEST_NUM; i++) {
                list.add(rand.nextInt(TEST_NUM));
            }
        }
    }
}