package com.dumaisoft.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2018/2/1
 * Create Time: 17:24
 * Description:
 */
public class Lambda1 {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello Lambda Expressions");
        new Thread(runnable).start();

        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();

//        这个例子向我们展示了Java 8 lambda表达式的语法。你可以使用lambda写出如下代码：
//
//        (params) -> expression
//        (params) -> statement
//        (params) -> { statements }

        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(s -> System.out.println(s));
        System.out.println("-----------------------");
        features.forEach(System.out::println);

    }
}
