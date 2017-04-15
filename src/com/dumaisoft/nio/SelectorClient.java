package com.dumaisoft.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/30
 * Create Time: 21:00
 * Description:
 */
public class SelectorClient {
    static class Client extends Thread {
        private String name;
        private Random random = new Random(47);

        Client(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                SocketChannel channel = SocketChannel.open();
                channel.configureBlocking(false);
                channel.connect(new InetSocketAddress(1234));
                while (!channel.finishConnect()) {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                for (int i = 0; i < 5; i++) {
                    TimeUnit.MILLISECONDS.sleep(100 * random.nextInt(10));
                    String str = "Message from " + name + ", number:" + i;
                    buffer.put(str.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        channel.write(buffer);
                    }
                    buffer.clear();
                }
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Client("Client-1"));
        executorService.submit(new Client("Client-2"));
        executorService.submit(new Client("Client-3"));
        executorService.shutdown();
    }
}
