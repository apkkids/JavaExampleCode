package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/14
 * Create Time: 18:12
 * Description:
 */
public class TimeClient {
    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;
    protected int port = DEFAULT_TIME_PORT;
    protected List<InetSocketAddress> remoteHosts;
    protected DatagramChannel channel;

    public TimeClient(String[] argv) throws Exception {
        if (argv.length == 0) {
            throw new Exception("Usage: [-p port] host ...");
        }
        parseArgs(argv);
        this.channel = DatagramChannel.open();
    }

    private void parseArgs(String[] argv) {
        remoteHosts = new LinkedList<>();
        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];
            if (arg.equals("-p")) {
                i++;
                this.port = Integer.parseInt(argv[i]);
                continue;
            }
            InetSocketAddress sa = new InetSocketAddress(arg, port);
            if (sa.getAddress() == null) {
                System.out.println("Can not resolve address: " + arg);
                continue;
            }
            remoteHosts.add(sa);
        }
    }

    protected InetSocketAddress receivePacket(DatagramChannel channel, ByteBuffer buffer) throws IOException {
        buffer.clear();
        return ((InetSocketAddress) channel.receive(buffer));
    }

    protected void sendRequest() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        Iterator it = remoteHosts.iterator();
        while (it.hasNext()) {
            InetSocketAddress sa = (InetSocketAddress) it.next();
            System.out.println("Requesting time from " + sa.getHostName() + ":" + sa.getPort());
            buffer.clear().flip();
            channel.send(buffer, sa);
        }
    }

    public void getReplies() throws Exception {
        ByteBuffer longBuffer = ByteBuffer.allocate(8);
        longBuffer.order(ByteOrder.BIG_ENDIAN);
        longBuffer.putLong(0, 0);
        longBuffer.position(4);
        ByteBuffer buffer = longBuffer.slice();
        int expect = remoteHosts.size();
        int replies = 0;
        System.out.println();
        System.out.println("Waiting for replies...");
        while (true) {
            InetSocketAddress sa;
            sa = receivePacket(channel, buffer);
            buffer.flip();
            replies++;
            printTime(longBuffer.getLong(0), sa);
            if (replies == expect) {
                System.out.println("All packets answered");
                break;
            }
            System.out.println("Recieved " + replies + " of " + expect + " replies");
        }
    }

    protected void printTime(long remote1900, InetSocketAddress sa) {
        long local = System.currentTimeMillis() / 1000;
        long remote = remote1900 - DIFF_1900;
        Date remoteDate = new Date(remote * 1000);
        Date localDate = new Date(local * 1000);
        long skew = remote - local;
        System.out.println("Reply from " + sa.getHostName() + ":" + sa.getPort());
        System.out.println(" there: " + remoteDate);
        System.out.println(" here: " + localDate);
        System.out.println(" skew: ");
        if (skew == 0) {
            System.out.println("none");
        } else if (skew > 0) {
            System.out.println(skew + " seconds ahead");
        } else {
            System.out.println((-skew) + " seconds behind");
        }
    }

    public static void main(String[] args) throws Exception {
        TimeClient client = new TimeClient(args);
        client.sendRequest();
        client.getReplies();
    }
}
