package com.dumaisoft.nio;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/15
 * Create Time: 22:48
 * Description:
 */
public class SimplePipe {
    public static void main(String[] args) throws IOException {
        //创建一个管道，并拿到管道两端的channel
        Pipe pipe = Pipe.open();
        WritableByteChannel writableByteChannel = pipe.sink();
        ReadableByteChannel readableByteChannel = pipe.source();

        //创建一个线程从sink端写入数据
        WorkerThread thread = new WorkerThread(writableByteChannel);
        thread.start();


        //主线程从source端读取数据，并组成String打印
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while ( readableByteChannel.read(buffer) >= 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String str = new String(bytes);
            System.out.println(str);
            buffer.clear();
        }
        readableByteChannel.close();
    }

    private static class WorkerThread extends Thread {
        WritableByteChannel channel;

        public WorkerThread(WritableByteChannel writableByteChannel) {
            this.channel = writableByteChannel;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            for (int i = 0; i < 10; i++) {
                String str = "pipe sink data " + i;

                buffer.put(str.getBytes());
                buffer.flip();
                try {
                    channel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buffer.clear();
            }
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
