package com.dumaisoft.stream;

import java.io.*;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/11/22
 * Create Time: 21:15
 * Description:
 */
public class DigestStreamExam {
    public static void main(String[] args) {
        String str = "Today is a good day. 今天是个好天气。";
        digestString(str);
        File file = new File("d:\\a.txt");
        digestFile(file);
    }

    private static void digestFile(File file) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, sha);

            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                digestOutputStream.write(buf, 0, len);
            }
            digestOutputStream.flush();
            digestOutputStream.close();
            byte[] result = digestOutputStream.getMessageDigest().digest();
            for (int i = 0; i < result.length; i++) {
                System.out.print(byteToHexString(result[i]));
            }
            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void digestString(String str) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, sha);

            byte[] bytes = str.getBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = byteArrayInputStream.read(buf)) != -1) {
                digestOutputStream.write(buf, 0, len);
            }
            digestOutputStream.flush();
            digestOutputStream.close();
            byte[] result = digestOutputStream.getMessageDigest().digest();
            for (int i = 0; i < result.length; i++) {
                System.out.print(byteToHexString(result[i]));
            }
            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String byteToHexString(byte b) {
        String s = Integer.toHexString(b);
        int len = s.length();
        if (len >= 2) {
            s = s.substring(len - 2);
        } else {
            s = "0" + s;
        }
        return s;
    }
}
