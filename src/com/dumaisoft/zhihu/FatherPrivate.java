package com.dumaisoft.zhihu;

import com.dumaisoft.concurrency.UnSafeExam;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/4
 * Create Time: 21:23
 * Description:
 */
public class FatherPrivate {
    static class Father {
        public int getMyPrivate() {
            return myPrivate;
        }

        public void setMyPrivate(int myPrivate) {
            this.myPrivate = myPrivate;
        }

        private int myPrivate = 47;
    }

    static class Son extends Father {
    }

    public static void main(String[] args) {
        Son son = new Son();
        try {
            System.out.println("using reflection:");
            //使用反射从父类中获取私有的Field，然后从子类对象中得到该属性
            Field field = Father.class.getDeclaredField("myPrivate");
            field.setAccessible(true);
            int fatherPrivate = (int) field.get(son);
            System.out.println(fatherPrivate);

            //在子类中调用方式设置属性，然后使用反射得到
            son.setMyPrivate(48);
            fatherPrivate = (int) field.get(son);
            System.out.println(fatherPrivate);

            //直接使用反射设置私有属性，然后用子类的方法访问属性
            field.set(son, 49);
            System.out.println(son.getMyPrivate());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //使用Unsafe
        Unsafe unsafe = null;
        try {
            System.out.println("use unsafe:");
            //得到Unsafe的实例
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);

            //使用Unsafe得到类中属性的地址偏移量，然后操作Son对象的属性
            long x_addr = unsafe.objectFieldOffset(Father.class.getDeclaredField("myPrivate"));
            int fatherPrivate = unsafe.getInt(son, x_addr); //读取
            System.out.println(fatherPrivate);
            unsafe.putInt(son, x_addr, 50);      //写入
            System.out.println(son.getMyPrivate());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
