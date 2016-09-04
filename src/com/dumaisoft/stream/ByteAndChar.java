package com.dumaisoft.stream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/9/4
 * Create Time: 20:34
 * Description:
 */
public class ByteAndChar {
    public static void main(String[] args) {
//        listAllByteValue();
//        byteGetValue();
//        listCharValue();
//        charGetValue();
//        charToBytes();
        showNativeEncoding();
//        saveCharToFile();
//        byteToChar();
    }

    private static void saveCharToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("d:\\char.txt");
            char c = 0x4e00;
            byte[] buf = (c + "").getBytes("unicode");
            for (int i = 0; i < buf.length; i++) {
                System.out.println(byteToHexString(buf[i]));
            }
            fos.write(buf, 0, buf.length);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showNativeEncoding() {
        String enc = System.getProperty("file.encoding");
        System.out.println(enc);
    }

    public static void charToBytes() {
        try {
            byte[] buf1 = "一".getBytes("unicode");
            System.out.println("---------unicode---------");
            for (int i = 0; i < buf1.length; i++) {
                System.out.println(Integer.toHexString(buf1[i]));
            }

            System.out.println("---------UTF-8---------");
            byte[] buf2 = "一".getBytes("UTF-8");
            for (int i = 0; i < buf2.length; i++) {
                System.out.println(Integer.toHexString(buf2[i]));
            }

            System.out.println("---------UTF-16---------");
            byte[] buf3 = "一".getBytes("UTF-16");
            for (int i = 0; i < buf3.length; i++) {
                System.out.println(Integer.toHexString(buf3[i]));
            }

            System.out.println("---------gbk---------");
            byte[] buf4 = "一".getBytes("gbk");
            for (int i = 0; i < buf4.length; i++) {
                System.out.println(Integer.toHexString(buf4[i]));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void charGetValue() {
        //把'一'赋值给char
        char c1 = '一';
        System.out.println(c1);

        char c2 = 0b100111000000000;
        System.out.println(c2);

        char c3 = 047000;
        System.out.println(c3);

        char c4 = 19968;
        System.out.println(c4);

        char c5 = 0x4e00;
        System.out.println(c5);

        char c6 = '\u4e00';
        System.out.println(c6);

        int i1 = (int) '一';
        System.out.println(Integer.toHexString(i1));
    }

    public static void listCharValue() {
        System.out.println("char max value is :" + (int) Character.MAX_VALUE + ", min value is :" + (int) Character.MIN_VALUE);

        for (char c = 19968; c < 20271; c++) {
            System.out.print(c);
        }
    }


    public static void other() {
        byte b1 = (byte) 0x80;  //-128=1000,0000=
        System.out.println(byteToBinaryString(b1));

        byte b2 = (byte) 0xf6;
        System.out.println(byteToBinaryString(b2));

        byte b3 = (byte) 0xfe;
        System.out.println(byteToBinaryString(b3));

        byte b4 = (byte) 0b10000000;
        System.out.println(byteToBinaryString(b4));

        byte b5 = (byte) '\ufefe';
        System.out.println(byteToBinaryString(b5));

        int i5 = (int) '\ufefe';
        System.out.println(Integer.toBinaryString(i5) + ",int=" + i5);


        byte b6 = (byte) i5;
        System.out.println(byteToBinaryString(b6) + ",byte=" + b6);
    }

    public static void byteGetValue() {
        //二进制以0b开头
        byte b1 = 0b00101010;
        System.out.println("b1:" + b1 + ",Binary:" + byteToBinaryString(b1));
        //八进制以0开头
        byte b2 = 052;
        System.out.println("b2:" + b2 + ",Binary:" + byteToBinaryString(b2));
        //十进制
        byte b3 = 42;
        System.out.println("b3:" + b3 + ",Binary:" + byteToBinaryString(b3));
        //十六进制
        byte b4 = 0x2a;
        System.out.println("b4:" + b4 + ",Binary:" + byteToBinaryString(b4));

        //-42的赋值
        //二进制，由于11010110以原码来理解已经超过了127，因此必须使用byte进行强制类型转换
        byte b5 = (byte) 0b11010110;
        System.out.println("b5:" + b5 + ",Binary:" + byteToBinaryString(b5));
        //八进制以0开头
        byte b6 = -052;
        System.out.println("b6:" + b6 + ",Binary:" + byteToBinaryString(b6));
        //十进制
        byte b7 = -42;
        System.out.println("b7:" + b7 + ",Binary:" + byteToBinaryString(b7));
        //十六进制，由于0xd6以原码来理解已经超过了127，因此必须使用byte进行强制类型转换
        byte b8 = (byte) 0xd6;
        System.out.println("b8:" + b8 + ",Binary:" + byteToBinaryString(b8));

        //将两个int转为byte的示例，示例告诉我们int转byte，就是简单的截取最后8位
        int i1 = 0b001011010110;
        int i2 = 0b110011010110;
        System.out.println("i1 = " + i1 + ", i2 = " + i2);
        byte b9 = (byte) i1;
        byte b10 = (byte) i2;
        System.out.println("b9 = " + b9 + ", b10 = " + b10);
    }

    public static void listAllByteValue() {
        for (int i = -128; i < 128; i++) {
            byte b = (byte) i;
            System.out.println("byte:" + b + ",Binary:" + byteToBinaryString(b));
        }
    }

    public static String byteToBinaryString(byte b) {
        String s = Integer.toBinaryString(b);
        if (b < 0) {
            s = s.substring(24);
        } else {
            if (s.length() < 8) {
                int len = s.length();
                for (int i = 0; i < 8 - len; i++) {
                    s = "0" + s;
                }
            }
        }
        return s;
    }

    public static String byteToHexString(byte b) {
        String s = Integer.toHexString(b);
        int len = s.length();
        if (len >= 2) {
            s = s.substring(len - 2);
        }else{
            s = "0"+s;
        }
        return s;
    }

    public static void byteToChar() {
        byte[] unicode_b = new byte[4];
        unicode_b[0] = (byte) 0XFE;
        unicode_b[1] = (byte) 0XFF;
        unicode_b[2] = (byte) 0X4E;
        unicode_b[3] = (byte) 0X2D;
        String unicode_str;

        byte[] gbk_b = new byte[2];
        gbk_b[0] = (byte) 0XD6;
        gbk_b[1] = (byte) 0XD0;
        String gbk_str;

        byte[] utf_b = new byte[3];
        utf_b[0] = (byte) 0XE4;
        utf_b[1] = (byte) 0XB8;
        utf_b[2] = (byte) 0XAD;
        String utf_str;

        try {
            unicode_str = new String(unicode_b, "unicode");
            System.out.println("unicode string is:" + unicode_str);
            gbk_str = new String(gbk_b, "gbk");
            System.out.println("gbk string is:" + gbk_str);
            utf_str = new String(utf_b, "utf-8");
            System.out.println("utf-8 string is:" + utf_str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}