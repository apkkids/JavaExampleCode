package com.dumaisoft.algorithm;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/5/19
 * Create Time: 21:19
 * Description: get the md5 code from a string.
 */
public class MD5Util {
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(GetMD5Code("000000"));
        System.out.println(GetMD5Code("000001"));
        String strVeryLong = "The MD5 message-digest algorithm is a widely used vulnerable cryptographic hash function producing a 128-bit (16-byte) hash value, typically expressed in text format as a 32-digit hexadecimal number. MD5 has been utilized in a wide variety of cryptographic applications and is also commonly used to verify data integrity.\n" +
                "\n" +
                "MD5 is a one-way function; it is neither encryption nor encoding. It can be reversed by brute-force attack and suffers from extensive vulnerabilities as detailed in the security section below.\n" +
                "\n" +
                "MD5 was designed by Ronald Rivest in 1991 to replace an earlier hash function MD4.[3] The source code in RFC 1321 contains a \"by attribution\" RSA license.\n" +
                "\n" +
                "The security of the MD5 has been severely compromised, with its weaknesses having been exploited in the field, most infamously by the Flame malware in 2012. The CMU Software Engineering Institute considers MD5 essentially \"cryptographically broken and unsuitable for further use.";
        System.out.println(GetMD5Code(strVeryLong));
        System.out.println(GetMD5Code(strVeryLong).length());
    }
}
