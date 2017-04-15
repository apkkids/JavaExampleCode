package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/18
 * Create Time: 20:16
 * Description:
 */
public class BlockingChannelClient {
    public static void main(String[] args) {
        try {
            SocketChannel sc = SocketChannel.open();
            sc.connect(new InetSocketAddress("127.0.0.1", 1234));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            writeString(buffer, sc,"hello");
            writeString(buffer, sc,"world");
            writeString(buffer, sc,"exit");
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeString(ByteBuffer buffer, SocketChannel sc,String str) {
        buffer.clear();
        buffer.put(str.getBytes()).flip();
        try {
            sc.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
