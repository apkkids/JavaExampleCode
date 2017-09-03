package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/6/28
 * Create Time: 22:49
 * Description:
 */
public class TrueOrFalse {
    public static void main(String[] args) {
        Integer i3 = 100;
        Integer i4 = 100;
        System.out.println(i3 == i4);//true,because of Integer cache
        Integer i5 = 1000;
        Integer i6 = 1000;
        System.out.println(i5 == i6);//false
        System.out.println(i5.equals(i6));
    }
}
