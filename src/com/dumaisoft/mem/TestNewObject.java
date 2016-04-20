package com.dumaisoft.mem;


/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/19
 * Create Time: 20:36
 * Description:
 */
public class TestNewObject {
    public static void main(String[] args) {

        for (int i = 0; i < 1000000; i++) {
            String str = new String("test");
            if (i % 100000 == 0) {
                System.gc();
                String memUsage = String.format("maxMemory=%d,totalMemory=%d,freeMemory=%d", Runtime.getRuntime().maxMemory(),
                        Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory());
                System.out.println(memUsage);
            }
        }
    }
}
