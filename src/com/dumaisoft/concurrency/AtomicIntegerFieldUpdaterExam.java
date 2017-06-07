package com.dumaisoft.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/7
 * Create Time: 21:31
 * Description:
 */
public class AtomicIntegerFieldUpdaterExam {
    public static void main(String[] args) throws InterruptedException {
        Student student = new Student(0, "Alex Wang");
        AtomicIntegerFieldUpdater<Student> updater = AtomicIntegerFieldUpdater.newUpdater(Student.class, "id");
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        updater.getAndIncrement(student);
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(student);
    }

    private static class Student {
        volatile int id;
        String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student id = " + id + ",name = " + name;
        }
    }
}
