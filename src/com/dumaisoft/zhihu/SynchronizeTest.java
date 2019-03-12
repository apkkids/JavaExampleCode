package com.dumaisoft.zhihu;

/**
 * Created by wxb on 2019/1/13 0013.
 */
public class SynchronizeTest {
    //第一种，使用一个互斥对象锁住代码块；它可以这么理解，多个线程想访问互斥代码块，必须拿到mutex对象的锁。
    // 然后，后面的所有类型都可以用第一种类型来解释
    Object mutex = new Object();

    public void methodA() {
        synchronized (mutex) {
            //codeAAAAAAAAAAAAAAAAAAA
        }
    }

    //第二种，锁住整个非静态method
    public synchronized void methodB() {
        //codeBBBBBBBBBBBBBBBBB
    }
    //第二种相当于用this对象来锁住方法
    public void methodBB(){
        synchronized (this){
            //codeBBBBBBBBBBBBBBBBB
        }
    }

    //第三种，使用this锁住一个代码块，这个其实和第二种一样
    public void methodC() {
        synchronized (this) {
            //codeCCCCCCCCCCCCCCCCCC
        }
    }

    //第四种，锁住整个静态method
    public static synchronized void methodD() {
        //codeDDDDDDDDDDDDDDDDDDD
    }
    //第四种相当于用类对象锁住代码块
    public static void methodDD(){
        synchronized (SynchronizeTest.class){
            //codeDDDDDDDDDDDDDDDDDDD
        }
    }
}
