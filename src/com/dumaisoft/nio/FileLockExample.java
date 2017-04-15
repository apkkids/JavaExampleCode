package com.dumaisoft.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/16
 * Create Time: 20:57
 * Description:
 */
public class FileLockExample {
    private static String filepath = "D:\\tmp\\filelock.txt";
    private static Random rand = new Random();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: [-r | -w]");
            System.exit(1);
        }
        boolean isWriter = args[0].equals("-w");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filepath, (isWriter) ? "rw" : "r");
            FileChannel channel = randomAccessFile.getChannel();
            if (isWriter) {
                lockAndWrite(channel);
            } else {
                lockAndRead(channel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void lockAndWrite(FileChannel channel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            int i=0;
            while (true) {
                System.out.println("Writer try to lock file...");
                FileLock lock = channel.lock(0,4,false);

                buffer.putInt(0,i);
                buffer.position(0).limit(4);
                System.out.println("buffer is :"+buffer);
                channel.write(buffer,0);
                channel.force(true);
                buffer.clear();
                System.out.println("Writer write :" + i++);

                lock.release();
                System.out.println("Sleeping...");
                TimeUnit.SECONDS.sleep(rand.nextInt(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void lockAndRead(FileChannel channel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            while (true) {
                System.out.println("Reader try to lock file...");
                FileLock lock = channel.lock(0,4,true);

                buffer.clear();
                channel.read(buffer,0);
                buffer.flip();
                System.out.println("buffer is:"+buffer);
                int i = buffer.getInt(0);
                System.out.println("Reader read :" + i);

                lock.release();
                System.out.println("Sleeping...");
                TimeUnit.SECONDS.sleep(rand.nextInt(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
