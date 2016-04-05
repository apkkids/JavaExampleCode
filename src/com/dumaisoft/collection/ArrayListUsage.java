package com.dumaisoft.collection;

import com.dumaisoft.reflection.ReflectionUtils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/3
 * Create Time: 22:15
 * Description: Introduce the base usage of ArrayList.
 */
public class ArrayListUsage {
    public static void main(String[] args) {
        construct();
        addElement();
        removeElement();
        changeElement();
    }

    private static void changeElement() {
        ReflectionUtils.printMethodName();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println("before:"+list);
        list.set(1,"x");
        System.out.println("after change:"+list);
    }

    private static void removeElement() {
        ReflectionUtils.printMethodName();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println("before:"+list);

        //remove by index
        list.remove(1);
        System.out.println("after remove:"+list);

        //remove by value
        list.add(1,"1");
        System.out.println("before:"+list);
        list.remove("3");
        System.out.println("after remove:"+list);
    }

    private static void addElement() {
        ReflectionUtils.printMethodName();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println("before:"+list);

        list.add(1,"e");
        System.out.println("after add:"+list);
    }

    private static void construct() {
        ReflectionUtils.printMethodName();
        //first construct method
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println(list);

        //second construct method
        ArrayList<String> list1 = new ArrayList<>();
        list1.addAll(list);
        System.out.println(list1);

        //third construct method
        ArrayList<String> list2 = new ArrayList<>(list1);
        System.out.println(list2);

        //also,you can initialize an ArrayList with another Collection object
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(String.valueOf(i));
        }
        System.out.println("set is "+set);
        ArrayList<String> list3 = new ArrayList<>(set);
        System.out.println(list3);
    }
}
