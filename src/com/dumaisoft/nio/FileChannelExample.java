package com.dumaisoft.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/12
 * Create Time: 20:58
 * Description: the example of FileChannel usage
 */
public class FileChannelExample {
    public static void main(String[] args) throws IOException {
//        testChannelCreate();
//        testFilePosition();
//        testFileHole();
        testFileCopy();
    }

    private static void testFileHole() {
        final String filepath = "D:\\tmp\\filehole.txt";
        try {
            //create a file with 26 char a~z
            FileOutputStream fos = new FileOutputStream(filepath);
            StringBuilder sb = new StringBuilder();
            for (char c = 'a'; c <= 'z'; c++) {
                sb.append(c);
            }
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();

            //creat FileChannel
            RandomAccessFile file = new RandomAccessFile(filepath, "rw");
            System.out.println("file length is:"+file.length());
            FileChannel channel = file.getChannel();

            //wirte a byte at position 100
            channel.position(100);
            channel.write((ByteBuffer) ByteBuffer.allocate(1).put((byte) 0).flip());
            System.out.println("file position in RandomAccessFile is :" + file.getFilePointer());
            System.out.println("file length is:"+file.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testFilePosition() {
        final String filepath = "D:\\tmp\\a.txt";
        try {
            //create a file with 26 char a~z
            FileOutputStream fos = new FileOutputStream(filepath);
            StringBuilder sb = new StringBuilder();
            for (char c = 'a'; c <= 'z'; c++) {
                sb.append(c);
            }
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();

            //creat FileChannel
            RandomAccessFile file = new RandomAccessFile(filepath, "rw");
            FileChannel channel = file.getChannel();
            System.out.println("file position in FileChannel is :" + channel.position());
            file.seek(5);
            System.out.println("file position in FileChannel is :" + channel.position());
            channel.position(10);
            System.out.println("file position in RandomAccessFile is :" + file.getFilePointer());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testChannelCreate() throws IOException {
        final String filepath = "D:\\tmp\\a.txt";

        RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, "rw");
        FileChannel readAndWriteChannel = randomAccessFile.getChannel();
        FileInputStream fis = new FileInputStream(filepath);
        FileChannel readChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream(filepath);
        FileChannel writeChannel = fos.getChannel();

        readAndWriteChannel.close();
        readChannel.close();
        writeChannel.close();
    }

    private static void testFileCopy() throws IOException {
        RandomAccessFile source = new RandomAccessFile("D:\\tmp\\a.txt", "r");
        RandomAccessFile dest = new RandomAccessFile("D:\\tmp\\b.txt", "rw");
        FileChannel srcChannel = source.getChannel();
        FileChannel destChannel = dest.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        destChannel.write(buffer, 9);

        while (srcChannel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                destChannel.write(buffer);
            }
            buffer.clear();
        }
        srcChannel.close();
        destChannel.close();
    }
}
