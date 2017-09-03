package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/8
 * Create Time: 22:35
 * Description:
 */
public class SingletonTest {
    public static void main(String[] args) {
        Singleton someone = Singleton.getInstance();
        System.out.println("someone get Singleton = " + someone);
        Singleton anotherone = Singleton.getInstance();
        System.out.println("another one get Singleton = " + anotherone);
        if (someone.equals(anotherone)) {
            System.out.println("they are the same one.");
        }
    }
}

class Singleton{
    private static Singleton instance = null;
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
