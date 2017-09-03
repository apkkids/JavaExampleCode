package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/24
 * Create Time: 19:33
 * Description:
 */
public class ClassTest {
    private void test(Class<? extends ClassFather> from) {
        try {
            from.newInstance().getFatherName();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <T extends ClassFather> void test(T from) {
        from.getFatherName();
    }

    public static void main(String[] args) {
        ClassTest test = new ClassTest();
        test.test(new ClassFather());
        test.test(ClassFather.class);
    }
}