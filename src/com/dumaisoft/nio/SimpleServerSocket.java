package com.dumaisoft.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/16
 * Create Time: 21:35
 * Description: Traditional Socket Server example
 */
public class SimpleServerSocket {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(1234));
            Socket socket = serverSocket.accept();
            System.out.println("accept connection from:" + socket.getRemoteSocketAddress());
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (is.read(bytes) != -1) {
                String str = new String(bytes);
                if (str.equals("exit")) {
                    break;
                }
                System.out.println(str);
            }
            is.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
