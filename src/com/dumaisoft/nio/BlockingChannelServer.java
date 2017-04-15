package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/18
 * Create Time: 20:13
 * Description:
 */
public class BlockingChannelServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(1234));
            SocketChannel sc = ssc.accept();
            System.out.println("accept connection from:" + sc.getRemoteAddress());
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (sc.read(buffer) != -1) {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                System.out.println(new String (bytes));
                buffer.clear();
            }
            sc.close();
            ssc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
