package com.dumaisoft.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.lang.String;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2018/2/1
 * Create Time: 17:35
 * Description:
 */
public class Predicate1 {
    public static void main(String[] args) {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> ((String) str).startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str) -> ((String) str).endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str) -> true);

        System.out.println("Print no language : ");
        filter(languages, (str) -> false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> ((String) str).length() > 4);
    }

    public static void filter(List<String> names, Predicate condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }
}
