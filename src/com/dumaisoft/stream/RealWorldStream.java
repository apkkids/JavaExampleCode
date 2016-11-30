package com.dumaisoft.stream;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/11/30
 * Create Time: 10:21
 * Description:
 */
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by test2 on 2016/9/2.
 * 真实世界的流使用范例
 */
public class RealWorldStream {
    public static void main(String[] args) {
        String srcFile = "D:\\src.txt";
        String desFile = "D:\\src.des.gz";
        makeSourceFile(srcFile);

        //密码和盐
        String password = "This is my key";
        byte[] salt = initSalt();
        //加密压缩保存
        gzipCipherFile(srcFile, desFile, password, salt);

        //解密解压，输出到屏幕
        ungzipCipherFile(desFile, password, salt);
    }

    private static void ungzipCipherFile(String desFile, String password, byte[] salt) {
        try {
            //创建密钥
            byte[] desKeyData = password.getBytes();
            Provider sunJce = new com.sun.crypto.provider.SunJCE();
            Security.addProvider(sunJce);
            char[] pbeKeyData = password.toCharArray();
            PBEKeySpec pbeKeySpec = new PBEKeySpec(pbeKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
            Cipher pbe = Cipher.getInstance("PBEWithMD5AndDES");
            pbe.init(Cipher.DECRYPT_MODE, pbeKey, paramSpec);

            BufferedInputStream bis = new BufferedInputStream(
                    new CipherInputStream(
                            new GZIPInputStream(
                                    new FileInputStream(desFile)),pbe));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff =new byte[1024];
            int len=0;
            while((len=bis.read(buff))!=-1){
                baos.write(buff,0,len);
            }
            baos.flush();
            String str = baos.toString("UTF-16");
            baos.close();
            bis.close();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    //产生加密所用的盐
    public static byte[] initSalt() {
        //实例化安全随机数
        SecureRandom random = new SecureRandom();
        //产出盐
        return random.generateSeed(8);
    }

    //将源文件进行加密压缩后保存到目标文件
    private static void gzipCipherFile(String srcFile, String desFile, String password, byte[] salt) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));

            //创建密钥
            byte[] desKeyData = password.getBytes();
            Provider sunJce = new com.sun.crypto.provider.SunJCE();
            Security.addProvider(sunJce);
            char[] pbeKeyData = password.toCharArray();
            PBEKeySpec pbeKeySpec = new PBEKeySpec(pbeKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
            Cipher pbe = Cipher.getInstance("PBEWithMD5AndDES");
            pbe.init(Cipher.ENCRYPT_MODE, pbeKey, paramSpec);

            BufferedOutputStream bos = new BufferedOutputStream(
                    new CipherOutputStream(
                            new GZIPOutputStream(
                                    new FileOutputStream(desFile)), pbe));
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    //创建一个程序中用来执行的源文件
    private static void makeSourceFile(String srcFile) {
        File file = new File(srcFile);
        String str = "Today is a good day. 今天天气很好。";
        try {
            //使用JAVA内部的编码标准UTF-16来进行char到byte的编码转换
            byte[] bytes = str.getBytes("UTF-16");

            //写入文件
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bais.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
