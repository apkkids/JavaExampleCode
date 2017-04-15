package com.dumaisoft.nio;

import java.nio.CharBuffer;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/1/2
 * Create Time: 20:19
 * Description:
 */
public class DuplicateBuffer {
    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate (8);
        buffer.put("abcdefgh");
        buffer.flip();
        drainBuffer(buffer);

//        buffer.position (3).limit (6).mark().position (5);

        buffer.position(1);
        buffer.put("xxx");
        buffer.flip();
        drainBuffer(buffer);

        CharBuffer dupeBuffer = buffer.duplicate();
//        dupeBuffer.position(0);
//        dupeBuffer.put("yyy");
//        dupeBuffer.flip();
//        drainBuffer(dupeBuffer);

    }

    private static void drainBuffer(CharBuffer buffer) {
        while (buffer.remaining() > 0) {
            System.out.print(buffer.get());
        }
        System.out.println();
    }
}
