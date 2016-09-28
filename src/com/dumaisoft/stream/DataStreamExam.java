package com.dumaisoft.stream;

import java.io.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/9/28
 * Create Time: 20:21
 * Description:
 */
public class DataStreamExam {
    public static void main(String[] args) {
        dataOutputStreamExam();
    }

    public static void dataOutputStreamExam() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            boolean b = true;
            dos.writeBoolean(b);
            showDataByte(baos);

            byte by = 0x03;
            dos.writeByte(by);
            showDataByte(baos);

            short sh = 42;
            dos.writeShort(sh);
            showDataByte(baos);

            char c = '一';
            dos.writeChar(c);
            showDataByte(baos);

            int i = 1234567;
            dos.writeInt(i);
            showDataByte(baos);

            long l = 12345678L;
            dos.writeLong(l);
            showDataByte(baos);

            float f = 3.1415926f;
            dos.writeFloat(f);
            showDataByte(baos);

            double dou = 3.1415926535;
            dos.writeDouble(dou);
            showDataByte(baos);

            String s = "This is a good day. 今天是个好天气.";
            dos.writeUTF(s);
            showDataByte(baos);

            int i2 =233;
            dos.writeInt(i2);
            showDataByte(baos);

            dos.flush();
            baos.flush();

            //依次读取数据
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
            b = dis.readBoolean();
            System.out.println(b);
            by = dis.readByte();
            System.out.println(by);
            sh =dis.readShort();
            System.out.println(sh);
            c=dis.readChar();
            System.out.println(c);
            i=dis.readInt();
            System.out.println(i);
            l=dis.readLong();
            System.out.println(l);
            f=dis.readFloat();
            System.out.println(f);
            dou=dis.readDouble();
            System.out.println(dou);
            s=dis.readUTF();
            System.out.println(s);
            i2=dis.readInt();
            System.out.println(i2);
            dis.close();

            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void showDataByte(ByteArrayOutputStream baos) {
        byte[] buf = baos.toByteArray();
        for (int j = 0; j < buf.length; j++) {
            System.out.print( byteToHexString(buf[j])+",");
        }
        System.out.println("------------------------------");
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
}
