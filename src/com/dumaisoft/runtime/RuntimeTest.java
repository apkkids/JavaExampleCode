package com.dumaisoft.runtime;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/15
 * Create Time: 21:35
 * Description: some runtime test code
 */
public class RuntimeTest {
    public static void main(String[] args) {
        int cpu_count = Runtime.getRuntime().availableProcessors();
        System.out.println("cpu count is " + cpu_count);
    }
}
