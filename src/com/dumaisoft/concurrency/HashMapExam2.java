package com.dumaisoft.concurrency;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/15
 * Create Time: 21:25
 * Description:
 */
public class HashMapExam2 {
    public static void main(String[] args) {
        Hashtable a;
        Map<Integer, String> map = new HashMap<>(10);
        System.out.println("map initialize");
        showMap(map);
        for (int i = 0; i < 20; i++) {
            map.put(i, new String(new char[]{(char) ('A'+ i)}));
            System.out.println("put "+i);
            showMap(map);
        }
    }

    private static void showMap(Map<Integer, String> map) {
        try {
            Field f = HashMap.class.getDeclaredField("table");
            f.setAccessible(true);
            Map.Entry<Integer,String>[] table= (Map.Entry<Integer, String>[]) f.get(map);
            f = HashMap.class.getDeclaredField("threshold");
            f.setAccessible(true);
            int threshold = (int) f.get(map);
            System.out.println("map size = "+map.size()+",threshold = "+threshold+",length="+table.length);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
