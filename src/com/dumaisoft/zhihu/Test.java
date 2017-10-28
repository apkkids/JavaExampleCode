package com.dumaisoft.zhihu;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/2/11
 * Create Time: 22:36
 * Description:
 */
public class Test {
    public static void main(String[] args0) {

        int index=9;
        System.out.println(roundUpToPowerOf2(67) );
        System.out.println(Integer.highestOneBit(-1));


//        int seed = sun.misc.Hashing.randomHashSeed(new Object());
//        System.out.println(seed);

        ConcurrentHashMap<Integer, String> hashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            hashMap.put(i, "String" + i);
        }
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println(entry);
        }
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;
    private static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        return number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }
}
