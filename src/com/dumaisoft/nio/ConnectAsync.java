package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/13
 * Create Time: 15:48
 * Description:
 */
public class ConnectAsync {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port =80;
        InetSocketAddress addr = new InetSocketAddress(host, port);
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        System.out.println("Initializing connect");
        sc.connect(addr);
        while (!sc.finishConnect()) {
            doSomethingUseful();
        }
        System.out.println("Connection established");
        //Doing something with the connected socket
        //The SocketChannel is still nonblocking
        sc.close();
    }

    private static void doSomethingUseful() {
        System.out.println("Doing something useless");
    }
}
