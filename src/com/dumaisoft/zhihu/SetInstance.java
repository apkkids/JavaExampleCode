package com.dumaisoft.zhihu;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/6
 * Create Time: 21:29
 * Description:
 */
public class SetInstance {
    public static void main(String[] args) {
        Map<Integer, String> all = new HashMap<>();
        all.put(1, "a");
        all.put(2, "b");
        all.put(3, "c");
        all.put(4, "d");
        all.put(null, "null");
        Set<Integer> set = all.keySet();
        //look here
        System.out.println(set.getClass().getName());
        Iterator<Integer> iter = set.iterator();
        System.out.println(iter.getClass().getName());

        while (iter.hasNext()) {
            Integer key = iter.next();
            System.out.println(key+"===>"+all.get(key));
        }
        Hashtable tabel;
        ConcurrentHashMap map;
    }
}
