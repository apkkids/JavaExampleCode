package com.dumaisoft.zhihu;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/10/17
 * Create Time: 17:07
 * Description:
 */
public class GetFraction {
    public static void main(String[] args) {
        getFraction("1234");
        getFraction("12.4231213344");
        getFraction("0.0001256");
    }

    public static void getFraction(String data) {
        int index = data.indexOf('.');
        if (index < 0) {
            data += ".0";
            index = data.indexOf('.');
        }

        //转换为BigDecimal，最大限度保留精度
        BigDecimal decimal = new BigDecimal(data);
        int rightLength = data.length() - index - 1;
        //求小数点左右的数
        String left = data.substring(0, index);
        String right = data.substring(index + 1);

        //求分子和分母
        BigInteger numerator = new BigInteger(left + right);
        BigInteger denominator = new BigInteger("10");
        denominator = denominator.pow(rightLength);

        //求最大公约数
        BigInteger gcd = numerator.gcd(denominator);

        //约分
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
        System.out.println(data+" is equal "+numerator+" / "+denominator);
    }
}
