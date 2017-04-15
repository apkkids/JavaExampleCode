package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/14
 * Create Time: 19:18
 * Description:
 */
public class TimeServer {
    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;
    protected DatagramChannel channel;

    public TimeServer(int port) throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.socket().bind(new InetSocketAddress(port));
        System.out.println("Listening on port " + port + " for time requests");
    }

    public void listen() throws Exception {
        ByteBuffer longBuffer = ByteBuffer.allocate(8);
        longBuffer.order(ByteOrder.BIG_ENDIAN);
        longBuffer.putLong(0, 0);
        longBuffer.position(4);
        ByteBuffer buffer = longBuffer.slice();
        while (true) {
            buffer.clear();
            SocketAddress sa = this.channel.receive(buffer);
            if (sa == null) {
                continue;
            }
            System.out.println("Time request from " + sa);
            buffer.clear();
            longBuffer.putLong(0, System.currentTimeMillis() / 1000 + DIFF_1900);
            this.channel.send(buffer, sa);
        }

    }

    public static void main(String[] args) {
        int port = DEFAULT_TIME_PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        try {
            TimeServer server = new TimeServer(port);
            server.listen();
        } catch (Exception e) {
            System.out.println("Can't bind to port " + port + ", try a different one");
        }
    }


}
