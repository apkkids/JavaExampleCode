package com.dumaisoft.concurrency;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/5/31
 * Create Time: 21:19
 * Description:
 */
public class UnSafeExam {
    public static void main(String[] args) throws InstantiationException, NoSuchFieldException {
        //获得一个UnSafe实例
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (unsafe != null) {
            try {
                //构造一个对象，且不调用其构造函数
                Test test = (Test) unsafe.allocateInstance(Test.class);
                //得到一个对象内部属性的地址
                long x_addr = unsafe.objectFieldOffset(Test.class.getDeclaredField("x"));
                //直接给此属性赋值
                unsafe.getAndSetInt(test, x_addr, 47);
                System.out.println(test.getX());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        //通过地址操作数组
        if (unsafe != null) {
            final int INT_BYTES = 4;
            int[] data = new int[10];
            System.out.println(Arrays.toString(data));
            long arrayBaseOffset = unsafe.arrayBaseOffset(int[].class);
            System.out.println("Array address is :" + arrayBaseOffset);
            unsafe.putInt(data, arrayBaseOffset, 47);
            unsafe.putInt(data, arrayBaseOffset + INT_BYTES * 8, 43);
            System.out.println(Arrays.toString(data));
        }

        //CAS
        if (unsafe != null) {
            Test test = (Test) unsafe.allocateInstance(Test.class);
            long x_addr = unsafe.objectFieldOffset(Test.class.getDeclaredField("x"));
            unsafe.getAndSetInt(test, x_addr, 47);
            unsafe.compareAndSwapInt(test, x_addr, 47, 78);
            System.out.println("After CAS:" + test.getX());
        }

    }

    static class Test {
        private final int x;

        Test(int x) {
            this.x = x;
            System.out.println("Test ctor");
        }
        int getX() {
            return x;
        }
    }
}
