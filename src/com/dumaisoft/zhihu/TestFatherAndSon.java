package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/31
 * Create Time: 22:24
 * Description:
 */
public class TestFatherAndSon {

    private static class Father{
        public void methods() {
            System.out.println("methods in Father");
        }
    }

    private static class Son extends Father {
        @Override
        public void methods() {
            System.out.println("methdos in Son");
        }
    }

    public static void main(String[] args) {
        Father obj = new Son();

        if (obj instanceof Father) {
            System.out.println("obj instanceof Father");
        }
        if (obj instanceof Son) {
            System.out.println("obj instanceof Son");
        }
        System.out.println("obj is "+obj.getClass().getName());

        obj.methods();
        Son obj2 = new Son();
        obj = obj2;
        obj.methods();
    }
}
