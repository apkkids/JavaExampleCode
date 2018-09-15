package com.dumaisoft.zhihu;

import java.math.BigInteger;

/**
 * Created by wxb on 2018/7/5 0005.
 */
public class Compute1994 {
    public static void main(String[] args) {
        String str="";
        for (int i = 0; i < 1994; i++) {
            str+="1994";
        }
        System.out.println(str);
        BigInteger bigInteger = new BigInteger(str);
        System.out.println(bigInteger);
        BigInteger x = bigInteger.mod(new BigInteger("7"));
        System.out.println(x);
    }
}
