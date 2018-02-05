package com.dumaisoft.concurrency;

/**
 * Created by wxb on 2018/1/22 0022.
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> localName = new ThreadLocal();
        try {
            localName.set("占小狼");
            // 其它业务逻辑

            String str = localName.get();
            System.out.println(str);

            new Thread(){
                @Override
                public void run() {
                    String strInThread = localName.get();
                    System.out.println(strInThread);
                }
            }.start();
        } finally {
            localName.remove();
        }
    }
}
