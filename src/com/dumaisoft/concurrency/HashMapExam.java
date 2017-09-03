package com.dumaisoft.concurrency;

import java.util.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/14
 * Create Time: 22:05
 * Description:
 */
public class HashMapExam {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>(10);
        for (int i = 0; i < 15; i++) {
            map.put(i, new String(new char[]{(char) ('A'+ i)}));
        }

        System.out.println("======keySet=======");
        Set<Integer> set = map.keySet();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("======values=======");
        Collection<String> values = map.values();
        Iterator<String> stringIterator=values.iterator();
        while (stringIterator.hasNext()) {
            System.out.println(stringIterator.next());
        }

        System.out.println("======entrySet=======");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}
