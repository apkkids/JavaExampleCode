package com.dumaisoft.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/12/30
 * Create Time: 23:51
 * Description:
 */
public class GenerateRandomMap {
    public static void main(String[] args) {
        Random rand = new Random(47);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 500; i++) {
            int number = rand.nextInt(21);
            if (map.get(number) == null) {
                map.put(number, 1);
            } else {
                int value = map.get(number);
                map.remove(number);
                map.put(number, value + 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry :
                map.entrySet()) {
            System.out.println(entry.getKey()+","+entry.getValue());
        }
    }
}
