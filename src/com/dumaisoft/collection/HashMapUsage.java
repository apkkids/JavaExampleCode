package com.dumaisoft.collection;

import com.dumaisoft.reflection.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/4
 * Create Time: 19:52
 * Description: Introduce the base usage of HashMap.
 */
public class HashMapUsage {
    public static void main(String[] args) {
        construct();
        addElement();
        removeElement();
        changeElement();
        traverseElement();
    }

    private static void traverseElement() {
        ReflectionUtils.printMethodName();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, "value" + i);
        }

        Set<String> keySet = map.keySet();
        for (String s : keySet
                ) {
            System.out.println(s + "," + map.get(s));
        }

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet
                ) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }
    }

    private static void changeElement() {
        ReflectionUtils.printMethodName();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, "value" + i);
        }
        System.out.println("before change:" + map);
        if (map.containsKey("key1")) {
//            map.replace("key1", "new-value");
        }
        System.out.println("after  change:" + map);
    }

    private static void removeElement() {
        ReflectionUtils.printMethodName();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, "value" + i);
        }
        System.out.println("before remove:" + map);
        if (map.containsKey("key1")) {
            map.remove("key1");
        }
        System.out.println("after  remove:" + map);
    }

    private static void addElement() {
        ReflectionUtils.printMethodName();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, "value" + i);
        }
        System.out.println("before add:" + map);
        map.put("key-new", "value-new");
        System.out.println("after  add:" + map);
    }

    private static void construct() {
        ReflectionUtils.printMethodName();
        //first construct method
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, "value" + i);
        }
        System.out.println(map);

        //second construct method
        Map<String, String> map1 = new HashMap<>();
        map1.putAll(map);
        System.out.println(map1);

        map1.clear();
        //third construct method
        map1 = new HashMap<>(map);
        System.out.println(map1);
    }
}
