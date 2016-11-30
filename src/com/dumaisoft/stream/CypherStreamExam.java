package com.dumaisoft.stream;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/11/30
 * Create Time: 10:11
 * Description:
 */
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class CypherStreamExam {
    public static void main(String[] args) {
        String infile = "D:\\a.txt";
        String outfile = "D:\\a.des";
        String password = "This is my key";
        byte[] salt = initSalt();
        encryptFile(infile, outfile, password, salt);

        infile = "D:\\a.des";
        outfile = "D:\\a.out";
        decryptFile(infile, outfile, password, salt);
    }

    //产生加密所用的盐
    public static byte[] initSalt() {
        //实例化安全随机数
        SecureRandom random = new SecureRandom();
        //产出盐
        return random.generateSeed(8);
    }

    private static void decryptFile(String infile, String outfile, String password, byte[] salt) {

        byte[] desKeyData = password.getBytes();
        try {
            FileInputStream fin = new FileInputStream(infile);
            FileOutputStream fout = new FileOutputStream(outfile);

            Provider sunJce = new com.sun.crypto.provider.SunJCE();
            Security.addProvider(sunJce);

            //创建密钥
            char[] pbeKeyData = password.toCharArray();
            PBEKeySpec pbeKeySpec = new PBEKeySpec(pbeKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);

            //解密
            Cipher pbe = Cipher.getInstance("PBEWithMD5AndDES");
            pbe.init(Cipher.DECRYPT_MODE, pbeKey, paramSpec);
            CipherOutputStream cout = new CipherOutputStream(fout, pbe);

            byte[] input = new byte[64];
            int len = 0;
            while ((len = fin.read(input)) != -1) {
                cout.write(input, 0, len);
            }
            cout.flush();
            cout.close();
            fin.close();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(String filein, String fileout, String password, byte[] salt) {
        byte[] desKeyData = password.getBytes();
        try {
            FileInputStream fin = new FileInputStream(filein);
            FileOutputStream fout = new FileOutputStream(fileout);

            Provider sunJce = new com.sun.crypto.provider.SunJCE();
            Security.addProvider(sunJce);

            //创建密钥
            char[] pbeKeyData = password.toCharArray();
            PBEKeySpec pbeKeySpec = new PBEKeySpec(pbeKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);

            //加密
            Cipher pbe = Cipher.getInstance("PBEWithMD5AndDES");
            pbe.init(Cipher.ENCRYPT_MODE, pbeKey, paramSpec);
            CipherOutputStream cout = new CipherOutputStream(fout, pbe);

            byte[] input = new byte[64];
            int len = 0;
            while ((len = fin.read(input)) != -1) {
                cout.write(input, 0, len);
            }
            cout.flush();
            cout.close();
            fin.close();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
