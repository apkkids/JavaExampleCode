package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/13
 * Create Time: 13:42
 * Description:
 */
public class Eggs {
    public static void main(String[] args) {
        int i = 10;
        while (true) {
            i++;
            if ((i % 2 == 1) && (i % 3 == 0) && (i % 4 == 1) && (i % 5 == 4) && (i % 6 == 3) && (i % 7 == 0) && (i % 8 == 1) && (i % 9 == 0)) {
                System.out.println(i);
            }
            if (i>=Integer.MAX_VALUE){
                break;
            }
        }

    }
}
