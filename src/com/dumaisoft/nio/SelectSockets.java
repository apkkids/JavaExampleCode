package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/16
 * Create Time: 19:46
 * Description:
 */
public class SelectSockets {
    public static int PORT_NUMBER = 1234;

    public static void main(String[] args) throws IOException {
        int port = PORT_NUMBER;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        System.out.println("Listening on port " + port);
        //创建三个重要对象
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        Selector selector = Selector.open();

        //绑定、配置、注册
        serverSocket.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = selector.select();
            if (n == 0) {
                continue;
            }
            Iterator it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();

                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    sayHello(channel);
                }
                if (key.isReadable()) {
                    readDataFromSocket(key);
                }
                it.remove();
            }
        }

    }

    private static void readDataFromSocket(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        buffer.clear();

        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip();
//            while (buffer.hasRemaining()) {
//                socketChannel.write(buffer);
//            }
            byte[] bytes = new byte[count];
            buffer.get(bytes);
            String str = new String(bytes);
            System.out.println("read data:"+str);
            buffer.clear();
        }
        if (count < 0) {
            socketChannel.close();
        }

    }

    private static void sayHello(SocketChannel channel) throws IOException {
//        buffer.clear();
//        buffer.put("HI there!\r\n".getBytes());
//        buffer.flip();
//        channel.write(buffer);
        System.out.println("hello,"+channel.socket().getRemoteSocketAddress());
    }

    private static void registerChannel(Selector selector, SocketChannel channel, int ops) throws IOException {
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

}
