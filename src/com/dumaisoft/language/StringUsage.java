package com.dumaisoft.language;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/4
 * Create Time: 18:58
 * Description: Introduce the common usage of String class.
 */
public class StringUsage {
    public static void main(String[] args) {
        construct();
        charAtString();
        compareString();
        concatString();
        endWithAndStartWith();
        equals();
        getBytes();
        indexOf();
        replaceString();
        splitString();
        subString();
        trimString();
        typeTransform();

    }

    private static void typeTransform() {
        System.out.println("---------------typeTransform---------------");
        int i = 10;
        String s = String.valueOf(i);
        System.out.println(s);
        char c = 'c';
        s = String.valueOf(c);
        System.out.println(s);
        boolean b = false;
        s = String.valueOf(b);
        System.out.println(s);

        //parse string to integer
        s = "10010";
        int x = Integer.parseInt("10010",2);
        System.out.println(x);
        x = Integer.parseInt(s);
        System.out.println(x);
    }

    private static void trimString() {
        System.out.println("---------------trimString---------------");
        String s = " ab cd ef   ";
        //trim does not cut the blank space in the string
        String s1 = s.trim();
        System.out.println(s1);
    }

    private static void subString() {
        System.out.println("---------------subString---------------");
        String s1 = "abcdefgh";
        String s2 = s1.substring(2);
        System.out.println(s2);
        s2 = s1.substring(2, 6);
        System.out.println(s2);
        //get the substring from index 1 to index (length - 1)
        s2 = s1.substring(1, s1.length() - 1);
        System.out.println(s2);
    }

    private static void splitString() {
        System.out.println("---------------splitString---------------");
        String s1 = "ab,cd,ef";
        String[] ss = s1.split(",");
        for (String s : ss
                ) {
            System.out.println(s);
        }

    }

    private static void replaceString() {
        System.out.println("---------------replaceString---------------");
        String s = "abcat";
        String s1 = s.replace('a', '1');
        System.out.println(s1);

        s = "abatbac";
        s1 = s.replaceAll("ba", "12");
        System.out.println(s1);

        s1 = s.replaceFirst("ba", "12");
        System.out.println(s1);
    }

    private static void indexOf() {
        System.out.println("---------------indexOf---------------");
        String s = "abcded";

        int index = s.indexOf('d');
        System.out.println(index);

        int index1 = s.indexOf('h');
        System.out.println(index1);

        //search 'd' from the fourth char
        index = s.indexOf('d', 4);
        System.out.println(index);

        //search 'd' from end of the string
        index = s.lastIndexOf('d');
        System.out.println(index);
    }

    private static void equals() {
        System.out.println("---------------equals---------------");
        String s1 = "abc";
        String s2 = "abc";
        if (s1.equals(s2)) {
            System.out.println(s1 + " equals " + s2);
        }
        s2 = "ABC";
        if (s1.equalsIgnoreCase(s2)) {
            System.out.println(s1 + " equalsIgnoreCase " + s2);
        }
    }

    private static void endWithAndStartWith() {
        System.out.println("---------------endWithAndStartWith---------------");
        String s1 = "StringUsage.java";
        if (s1.endsWith(".java")) {
            System.out.println(s1 + " is a java file.");
        }

        s1 = "Test.java";
        if (s1.startsWith("Test")) {
            System.out.println(s1 + " is a Test file.");
        }
    }

    private static void concatString() {
        System.out.println("---------------concatString---------------");
        String s1 = "abc";
        String s2 = "def";
        String s3 = s1.concat(s2);
        System.out.println(s3);

        //use + to concat String
        s3 = s1 + s2;
        System.out.println(s3);
        s3 = s1 + 1;
        System.out.println(s3);

    }

    private static void compareString() {
        System.out.println("---------------compareString---------------");
        String str1 = "abc";
        String str2 = "bcd";
        if (str1.compareTo(str2) > 0) {
            System.out.println(str1 + ">" + str2);
        } else {
            System.out.println(str1 + "<" + str2);
        }

        str1 = "abc";
        str2 = "ABC";
        if (str1.equalsIgnoreCase(str2)) {
            System.out.println(str1 + " equalsIgnoreCase " + str2);
        }
    }

    private static void charAtString() {
        System.out.println("---------------charAtString---------------");
        String str = "abcdef";
        char c = str.charAt(1);
        System.out.println(c);
    }

    private static void construct() {
        String str = new String("abc");
        System.out.println(str);
        str = "def";
        System.out.println(str);
    }

    public static void getBytes() {
        System.out.println("---------------getBytes---------------");
        String s1 = "abc";
        byte[] bytes = s1.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i]);
        }
        for (int i = 0; i < bytes.length; i++) {
            char c = (char) bytes[i];
            System.out.println(c);
        }
    }
}
