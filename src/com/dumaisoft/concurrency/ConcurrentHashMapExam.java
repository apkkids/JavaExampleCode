package com.dumaisoft.concurrency;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/16
 * Create Time: 20:53
 * Description:
 */
public class ConcurrentHashMapExam {
    public static void main(String[] args) {
        Map<Integer, String> map = new ConcurrentHashMap<>(4,0.75f,4);
        map.put(1, "Alex Wang");
        showMap(map);

    }

    private static void showMap(Map<Integer, String> map) {
        try {
            Field field = ConcurrentHashMap.class.getDeclaredField("segments");
            field.setAccessible(true);
            Object[] segments = (Object[]) field.get(map);
            System.out.println("map segments length = "+segments.length);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
