package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/31
 * Create Time: 22:17
 * Description:
 */
public class TestWhile {
    public static void main(String[] args) {
        int x = 1;
        int sum = 0;
        while (x <= 10) {
            sum = sum + x;
            x++;
        }
        System.out.println(sum + " ");
    }
}
