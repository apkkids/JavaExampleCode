package com.dumaisoft.lambda;

import java.util.function.BinaryOperator;

/**
 * Created by wxb on 2018/2/5 0005.
 * https://zhuanlan.zhihu.com/p/28093333
 *
 */
public class Lambda2 {
    public static void main(String[] args) {
        //1.
        Runnable r = () -> System.out.println("hi lambda");
        new Thread(r).start();
        //2.
        BinaryOperator<Integer> sumFunction = (x, y) -> x + y;
        System.out.println(sumFunction.apply(2,4));



    }
}
