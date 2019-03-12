package com.dumaisoft.zhihu;

import java.util.Random;

/**
 * Created by wxb on 2018/3/23 0023.
 * 蒙特卡洛方法求圆周率
 */
public class ComputePI {
    private static long num = 1000000000; //测试次数
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        long in = 0;
        for (long i = 0; i < num; i = i + 1) {
            //随机坐标
            float x = random.nextFloat();
            float y = random.nextFloat();
            //是否落入圆中
            if (Math.sqrt(x * x + y * y) <= 1) {
                in = in + 1;
            }
        }
        float f = in;
        f = 4 * f / num;
        System.out.println("in = " + in + ", total number = " + num);
        System.out.println("PI=" + f);
    }
}
