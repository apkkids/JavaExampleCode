package com.dumaisoft.zhihu;

/**
 * Created by wxb on 2019/1/11 0011.
 */
public class TestLambda {
    public static void main(String[] args) {
        new Thread(() -> {
            for (int x = 0; x < 10; x++) {
                System.out.println(Thread.currentThread().getName() + ".x=" + x);
            }
        }).start();
    }
}
