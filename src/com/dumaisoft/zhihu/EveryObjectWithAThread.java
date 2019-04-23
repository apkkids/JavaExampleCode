package com.dumaisoft.zhihu;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2019/4/23
 * Create Time: 17:20
 * Description:
 */
public class EveryObjectWithAThread {
    private static int ID_NUM = 1;
    static class TaskObject {
        int id;

        TaskObject() {
            id = ID_NUM++;
        }
        //任务对象要执行的方法
        void func()  {
            System.out.println("Task id:"+id+" is running.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task id:"+id+" is over.");
        }
    }

    public static void main(String[] args) {
        //建立一个对象数组
        ArrayList<TaskObject> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new TaskObject());
        }
        //为每个对象开启一个线程,一行代码搞定
        list.parallelStream().forEach(taskObject -> taskObject.func());
    }
}
