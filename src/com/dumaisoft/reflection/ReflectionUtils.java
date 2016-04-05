package com.dumaisoft.reflection;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/5
 * Create Time: 20:45
 * Description: provide some reflection function.
 */
public class ReflectionUtils {
    public static void main(String[] args) {
        printMethodName();
    }

    public static void printMethodName() {
        String methodName = "";
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length >= 3) {
            methodName = elements[2].getMethodName();
        }
        System.out.println("----------" + methodName + "----------");
    }
}
