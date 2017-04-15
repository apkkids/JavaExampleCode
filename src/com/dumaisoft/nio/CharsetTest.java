package com.dumaisoft.nio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/2/15
 * Create Time: 21:40
 * Description:Java字符集问题编程实例 文中的源代码
 */
public class CharsetTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        char c = '中';
        int i = c;
        System.out.println(i);
        System.out.println(Integer.toHexString(i));

        //encoding
        String str = c + "";
        byte[] bytes = str.getBytes("UTF-8");
        printBytes(bytes);
        bytes = str.getBytes("UTF-16BE");
        printBytes(bytes);
        bytes = str.getBytes("UTF-16LE");
        printBytes(bytes);
        bytes = str.getBytes("UTF-16");
        printBytes(bytes);
        bytes = str.getBytes("GBK");
        printBytes(bytes);
        bytes = str.getBytes("US-ASCII");
        printBytes(bytes);

        //set the charset
        String charset = "gb2312";
        setEncoding(charset);

        //get charsets supported by jdk
//        printAvailableCharsets();

        //encoding method
        byte[] bytes1 = encoding1("中国", "UTF-8");
        printBytes(bytes1);

        bytes1 = encoding2("中国", "UTF-8");
        printBytes(bytes1);

        //decoding method
        String decodeStr = decoding1(bytes1,"UTF-8");
        System.out.println(decodeStr);

        decodeStr = decoding2(bytes1, "UTF-8");
        System.out.println(decodeStr);

        //generate bad code
        generateBadCode();

        //three code
        threeCode();

    }

    private static void threeCode() throws UnsupportedEncodingException {
        String str = "中国good";
        byte[] bytes = str.getBytes("GBK");
        printBytes(bytes);
        bytes = str.getBytes("UTF-8");
        printBytes(bytes);
        bytes = str.getBytes("UTF-16");
        printBytes(bytes);
        bytes = str.getBytes("UTF-16BE");
        printBytes(bytes);
        bytes = str.getBytes("UTF-16LE");
        printBytes(bytes);
        bytes = str.getBytes("UTF-32");
        printBytes(bytes);
        char c = '中';
        int i = c;
        System.out.println(Integer.toHexString(i));
    }

    private static void generateBadCode() throws UnsupportedEncodingException {
        String str = "中国";
        byte[] bytes = str.getBytes("UTF-8");
        str = new String(bytes, "GBK");
        System.out.println(str);
    }

    private static String decoding2(byte[] bytes, String charset) {
        Charset cset = Charset.forName(charset);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        CharBuffer charBuffer = cset.decode(buffer);
        return charBuffer.toString();
    }

    private static String decoding1(byte[] bytes,String charset) throws UnsupportedEncodingException {
        String str = new String(bytes, charset);
        return str;
    }

    private static byte[] encoding2(String str, String charset) {
        Charset cset = Charset.forName(charset);
        ByteBuffer byteBuffer = cset.encode(str);
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return bytes;
    }

    private static byte[] encoding1(String str, String charset) throws UnsupportedEncodingException {
        return str.getBytes(charset);
    }

    private static void printAvailableCharsets() {
        Map<String, Charset> map = Charset.availableCharsets();
        System.out.println("the available Charsets supported by jdk:" + map.size());
        for (Map.Entry<String, Charset> entry :
                map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    private static void setEncoding(String charset) {
        Properties properties = System.getProperties();
        System.out.println(properties.get("file.encoding"));
        properties.put("file.encoding", charset);
        System.out.println(properties.get("file.encoding"));
    }

    private static void printBytes(byte[] bytes) {
        for (byte aByte : bytes) {
            int i = aByte & 0xff;
            System.out.print(Integer.toHexString(i) + " ");
        }
        System.out.println();
    }
}
