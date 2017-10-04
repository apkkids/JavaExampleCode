package com.dumaisoft.zhihu;

/**
 * Created by wxb on 2017/9/17 0017.
 */
public class TestFinally {
    public static void main(String[] args) {
        try {
            System.out.println("throw exception");
            throw new Exception("here");
        } catch (Exception e) {
            System.out.println("in catch,just return");
            return;
        }finally {
            System.out.println("finally must run.");
        }
    }
}
