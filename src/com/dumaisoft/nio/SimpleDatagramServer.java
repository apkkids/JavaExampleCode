package com.dumaisoft.nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/14
 * Create Time: 19:44
 * Description:
 */
public class SimpleDatagramServer {
    private static final int PORT = 37;

    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(PORT));

        ByteBuffer buffer = ByteBuffer.allocate(64);
        while (true) {
            buffer.clear();
            SocketAddress sa = channel.receive(buffer);
            if (sa == null) {
                continue;
            }

            buffer.flip();
            System.out.println("receive data from:" + sa);
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            String str = new String(bytes);
            System.out.println("receive data is :" + str);
        }
    }
}
