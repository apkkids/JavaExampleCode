package com.dumaisoft.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/12
 * Create Time: 22:45
 * Description:
 */
public class ChannelTransfer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: filename ...");
            return;
        }
        catFiles(Channels.newChannel(System.out), args);
    }

    private static void catFiles(WritableByteChannel target, String[] files) throws IOException {
        for (String file : files) {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fileChannel =fis.getChannel();
            fileChannel.transferTo(0, fileChannel.size(), target);
            fileChannel.close();
            fis.close();
        }
    }
}
