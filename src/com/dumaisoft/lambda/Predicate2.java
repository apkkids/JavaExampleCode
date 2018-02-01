package com.dumaisoft.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2018/2/1
 * Create Time: 17:49
 * Description:
 */
public class Predicate2 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        Predicate<String> startWithJ = (str) -> str.startsWith("J");
        Predicate<String> lengthEq4 = (str)->str.length()==4;

        names.stream().filter(startWithJ.and(lengthEq4)).forEach((n)->{
            System.out.println(n);
        });
    }
}
