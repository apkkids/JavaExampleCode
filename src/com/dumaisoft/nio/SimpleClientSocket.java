package com.dumaisoft.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/16
 * Create Time: 21:45
 * Description:
 */
public class SimpleClientSocket {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("127.0.0.1", 1234));
            OutputStream os = socket.getOutputStream();
            os.write("hello".getBytes());
            os.write("world".getBytes());
            os.write("exit".getBytes());
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
