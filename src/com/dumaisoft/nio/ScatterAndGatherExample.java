package com.dumaisoft.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/20
 * Create Time: 21:57
 * Description:
 */
public class ScatterAndGatherExample {
    public static void main(String[] args) throws UnsupportedEncodingException {
        ByteBuffer buffer1 = ByteBuffer.allocate(5);
        buffer1.put("hello".getBytes("GBK")).flip();
        ByteBuffer buffer2 = ByteBuffer.allocate(5);
        buffer2.put("world".getBytes("GBK")).flip();
        ByteBuffer[] buffers = {buffer1, buffer2};

        try {
            //gather example
            RandomAccessFile file = new RandomAccessFile("d:\\tmp\\scatter.txt", "rw");
            FileChannel channel = file.getChannel();
            channel.write(buffers);
            channel.force(false);
            channel.close();

            showFileContent("d:\\tmp\\scatter.txt");

            //scatter example
            buffer1.clear();
            buffer2.clear();
            file = new RandomAccessFile("d:\\tmp\\scatter.txt", "r");
            channel = file.getChannel();
            channel.read(buffers);
            String str1 = getBufferContent(buffer1);
            String str2 = getBufferContent(buffer2);
            System.out.println("buffer1 :" + str1);
            System.out.println("buffer2 :" + str2);
            channel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getBufferContent(ByteBuffer buffer) throws UnsupportedEncodingException {
        buffer.flip();
        System.out.println(buffer);
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes,"GBK");
    }

    private static void showFileContent(String filepath) {
        try {
            FileInputStream fis = new FileInputStream(filepath);
            byte[] bytes = new byte[1024];
            int len = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = fis.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            String str = baos.toString("GBK");
            System.out.println("file content:");
            System.out.println(str);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
