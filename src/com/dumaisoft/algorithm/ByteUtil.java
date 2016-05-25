package com.dumaisoft.algorithm;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/5/25
 * Create Time: 21:04
 * Description: provide the function byteToBinaryString and parseBinaryString, which can transfer from byte to string
 * or from string to byte.
 */
public class ByteUtil {
    public static void main(String[] args) {
        byte b = 127;
        System.out.println(byteToBinaryString(b));
        b = -128;
        System.out.println(byteToBinaryString(b));
        b = 0;
        System.out.println(byteToBinaryString(b));

        String binaryStr = "01111111";
        System.out.println(parseBinaryString(binaryStr));
        binaryStr = "10000000";
        System.out.println(parseBinaryString(binaryStr));
        binaryStr = "00000000";
        System.out.println(parseBinaryString(binaryStr));
    }

    public static String byteToBinaryString(byte b) {
        String s = "";
        for (int i = 7; i > -1; i--) {
            s += b >> i & 0x1;
        }
        return s;
    }

    public static byte parseBinaryString(String binaryStr) {
        if (null == binaryStr) {
            return 0;
        }
        int len = binaryStr.length();
        int result = 0;
        if (len != 8) {
            return 0;
        } else {
            //if the binaryStr >= 0
            if (binaryStr.charAt(0) == '0') {
                result = Integer.parseInt(binaryStr, 2);
            }
            //if the binaryStr < 0
            else {
                result = 256 - Integer.parseInt(binaryStr, 2);
            }
        }
        return (byte) result;
    }
}
