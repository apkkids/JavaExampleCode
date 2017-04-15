package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/16
 * Create Time: 20:06
 * Description:
 */
public class SelectSocketsClient {
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);
    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "127.0.0.1";
        int port = 1234;
        InetSocketAddress addr = new InetSocketAddress(host, port);
        SocketChannel sc = SocketChannel.open();

        System.out.println("Initializing connect");
        sc.connect(addr);
        sc.configureBlocking(false);

        int count = 0;
        while (sc.isConnected()) {
            writeData(sc,count);
            count++;
            if (count > 1) {
                break;
            }
        }

        //Doing something with the connected socket
        //The SocketChannel is still nonblocking
        sc.close();

    }

    private static void writeData(SocketChannel sc,int count) throws IOException {
        String str = "Hello, from client "+count;
        buffer.put(str.getBytes());
        buffer.flip();
        sc.write(buffer);
        System.out.println(str);
        buffer.clear();
    }


}
