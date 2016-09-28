package com.dumaisoft.stream;

import java.io.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/9/16
 * Create Time: 16:36
 * Description:
 */
public class ByteArrayStreamExam {

    public static void main(String[] args) {
//        byteArrayOutputStreamExam();
//        byteArrayInputStreamExam();
        stringReaderExam();
    }

    public static void stringReaderExam() {
        String str = "This is a good day.今天是个好天气。";
        StringReader stringReader = new StringReader(str);
        StringWriter stringWriter = new StringWriter();
        char[] buf = new char[128];
        int len = 0;
        try {
            while ((len = stringReader.read(buf)) != -1) {
                stringWriter.write(buf, 0, len);
            }
            stringWriter.flush();
            String result = stringWriter.toString();
            System.out.println(result);
            stringWriter.close();
            stringReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void byteArrayInputStreamExam() {
        byte[] buf = new byte[256];
        //赋值一段汉字的unicode编码，从4e00至4eff
        for (int i = 0x00; i < 0xff; i = i + 2) {
            buf[i] = 0x4e;
            buf[i + 1] = (byte) i;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] getbuf = new byte[128];
        int len = 0;
        try {
            while ((len = byteArrayInputStream.read(getbuf)) != -1) {
                byteArrayOutputStream.write(getbuf, 0, len);
            }
            byte[] result = byteArrayOutputStream.toByteArray();
            //将得到的字节转换为unicode编码的字符串
            String s = new String(result, "unicode");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void byteArrayOutputStreamExam() {
        try {
            FileInputStream fis = new FileInputStream("d:\\d.txt");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();
            byte[] result = baos.toByteArray();
            String s = new String(result);
            System.out.println(s);

            baos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
