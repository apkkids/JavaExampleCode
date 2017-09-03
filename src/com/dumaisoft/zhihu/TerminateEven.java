package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/14
 * Create Time: 20:27
 * Description:
 */
public class TerminateEven {
    private static int i;
    //静态同步方法
    public synchronized static void next() {
        i++;
        i++;
    }

    //静态同步方法
    public synchronized static int getValue() {
        return i;
    }

    public static void main(String[] args) {
        new Thread("Watcher") {
            public void run() {
                while (true) {
                    //子线程中循环调用getValue方法获取value的值，并进行检测，为偶数则退出程序
                    int value = TerminateEven.getValue();
                    if (value % 2 == 0) {
                        System.out.println(value);
                        System.exit(0);
                    }
                }
            }
        }.start();

        //主线程中循环调用next增加value的值，直至子线程导致整个程序退出
        while (true) TerminateEven.next();
    }
}