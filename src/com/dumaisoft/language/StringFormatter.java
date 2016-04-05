package com.dumaisoft.language;

import com.dumaisoft.reflection.ReflectionUtils;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/4/5
 * Create Time: 21:04
 * Description: give some example of String format.
 */
public class StringFormatter {
    public static void main(String[] args) {
        System.out.println("String format is %%[argument_index$][flags][width][.precision]conversion");
        conversionExample();
        argument_indexExample();
        flagsExample();
        widthExample();
        precisionExample();
    }

    private static void precisionExample() {
        ReflectionUtils.printMethodName();
        //float number precision
        System.out.format("%10.2f%n",12.3456);
        System.out.format("%010.2f%n",12.3456);
        System.out.format("%-10.2f%n",12.3456);
        System.out.format("%010.5f%n",12.3456);
        //string precision. width means total length, precision means how many characters shows.
        System.out.format("%10.5s%n","abcdef");
        System.out.format("%10.10s%n","abcdef");
        System.out.format("%-10.10s%n","abcdef");
        //notice, width can be expand to the real length, but precision can't.
        System.out.format("%-10.10s%n","abcdefghijklmn");
        System.out.format("%-10s%n","abcdefghijklmn");
    }

    private static void widthExample() {
        ReflectionUtils.printMethodName();
        System.out.format("%10d%n",10);
        System.out.format("%010d%n",10);
        System.out.format("%10s%n","abcd");
        System.out.format("%-10s%n","abcd");
        System.out.format("%10b%n",false);
    }

    private static void flagsExample() {
        ReflectionUtils.printMethodName();
        //flag can be + - 0 , ( # blank_space
        System.out.format("%+d%n",10);
        System.out.format("%+9d%n%-9d%n",10,10);

        System.out.format("%09d%n",10);

        System.out.format("%,d%n",123456);

        System.out.format("%(d%n",-123456);
        System.out.format("%(d%n",123456);


        System.out.format("%#x%n",0xff);
        System.out.format("%x%n",0xff);

        System.out.format("%#o%n",077);
        System.out.format("%o%n",077);

        System.out.format("% 10d%n",123456);

        //mix flag
        System.out.format("%+,10d%n",123456);
        System.out.format("%(,10d%n",-123456);
        System.out.format("%+,010d%n",123456);
    }

    private static void conversionExample() {
        ReflectionUtils.printMethodName();
        String s = "string";
        //%n equals to /n
        System.out.format("%s%n",s);
        char c = 'c';
        System.out.format("%c%n",c);
        boolean b = false;
        System.out.format("%b%n",b);
        int d = 10;
        System.out.format("%d%n",d);
        int x = 0xff;
        System.out.format("%x%n",x);
        int o = 077;
        System.out.format("%o%n",o);
        float f = (float) 99.99;
        System.out.format("%f%n",f);
        double db = 99.99;
        System.out.format("%f%n",db);
        double e = 2345.67;
        System.out.format("%e%n",e);
        System.out.format("%g%n",e);
        //%h means ASCII number
        System.out.printf("%h %h %h %h%n", 'a','b','c','d');
    }

    private static void argument_indexExample() {
        ReflectionUtils.printMethodName();
        System.out.format("%1$s-%3$s\n","s1","s2","s3");
        String s = String.format("%2$d",1,2,3);
        System.out.println(s);
    }
}
