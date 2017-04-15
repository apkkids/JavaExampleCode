package com.dumaisoft.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/3/7
 * Create Time: 21:30
 * Description: Buffer类的各种用法
 */
public class BufferExample {
    public static void main(String[] args) {
//        testProperties();
//        testMark();
//        testPut();
//        testGet();
//        mixPutAndGet();

//        testFlip();
//        testCompact();
//        testDuplicate();
//        testSlice();

//        testElementView();
//        testPutAndGetElement();
        testByteOrder();
    }

    private static void testByteOrder() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        //直接存入一个int
        buffer.putInt(0x1234abcd);
        buffer.position(0);
        int big_endian= buffer.getInt();
        System.out.println(Integer.toHexString(big_endian));
        buffer.rewind();
        int little_endian=buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
        System.out.println(Integer.toHexString(little_endian));

    }

    private static void testPutAndGetElement() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        //直接存入一个int
        buffer.putInt(0x1234abcd);
        //以byte分别取出
        buffer.position(0);
        byte b1 = buffer.get();
        byte b2 = buffer.get();
        byte b3 = buffer.get();
        byte b4 = buffer.get();
        System.out.println(Integer.toHexString(b1&0xff));
        System.out.println(Integer.toHexString(b2&0xff));
        System.out.println(Integer.toHexString(b3&0xff));
        System.out.println(Integer.toHexString(b4&0xff));
    }

    private static void testElementView() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        //存入四个字节,0x00000042
        buffer.put((byte) 0x00).put((byte) 0x00).put((byte) 0x00).put((byte) 0x42);
        buffer.position(0);
        //转换为IntBuffer，并取出一个int（四个字节）
        IntBuffer intBuffer = buffer.asIntBuffer();
        int i =intBuffer.get();
        System.out.println(Integer.toHexString(i));
    }

    /**
     * 缓冲区切片，将一个大缓冲区的一部分切出来，作为一个单独的缓冲区，但是它们公用同一个内部数组
     * 切片从原缓冲区的position位置开始，至limit为止。原缓冲区和切片各自拥有自己的属性。
     */
    private static void testSlice() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("abcdefghij");
        buffer.position(5);
        CharBuffer slice = buffer.slice();
        showBuffer(buffer);
        showBuffer(slice);
    }

    /**
     * 复制缓冲区，两个缓冲区对象实际上指向了同一个内部数组，但分别管理各自的属性
     */
    private static void testDuplicate() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("abcde");
        CharBuffer buffer1 = buffer.duplicate();
        buffer1.clear();
        buffer1.put("alex");
        showBuffer(buffer);
        showBuffer(buffer1);
    }

    /**
     * 测试压缩操作，压缩的含义是将读取了一部分的buffer，其剩下的部分整体挪动到buffer的头部（即从0开始的一段位置），便于后续的写入或者读取
     * 其含义为limit=limit-position，position=0
     */
    private static void testCompact() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("abcde");
        buffer.flip();
        //先读取两个字符
        buffer.get();
        buffer.get();
        showBuffer(buffer);
        //压缩
        buffer.compact();
        //继续写入
        buffer.put("fghi");
        buffer.flip();
        showBuffer(buffer);
        //从头读取后续的字符
        char[] chars = new char[buffer.remaining()];
        buffer.get(chars);
        System.out.println(chars);
    }

    /**
     * 测试flip操作，flip就是从写入转为读出前的一个设置buffer属性的操作，其意义是将limit=position，position=0
     */
    private static void testFlip() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("abc");
        buffer.flip();
        char[] chars = new char[buffer.remaining()];
        buffer.get(chars);
        System.out.println(chars);

        //以下操作与flip等同
        buffer.clear();
        buffer.put("abc");
        buffer.limit(buffer.position());
        buffer.position(0);
        chars = new char[buffer.remaining()];
        buffer.get(chars);
        System.out.println(chars);
    }

    /**
     * 混合读写也是合法的
     */
    private static void mixPutAndGet() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("abc");
        buffer.get();
        buffer.put("def");
        showBuffer(buffer);
        //读取此buffer的内容
        buffer.flip();
        char[] chars = new char[buffer.remaining()];
        buffer.get(chars);
        System.out.println(chars);
    }

    private static void testGet() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("abc");
        buffer.flip();
        //第一种读取方法
        char c1 = buffer.get();
        char c2 = buffer.get();
        char c3 = buffer.get();
        buffer.clear();
        //第二种读取方法
        buffer.put("abc");
        buffer.flip();
        char[] chars = new char[buffer.remaining()];
        buffer.get(chars);
        System.out.println(chars);
    }

    private static void testPut() {
        CharBuffer buffer = CharBuffer.allocate(10);
        //第一种put方法
        buffer.put('a').put('b').put('c');
        buffer.clear();
        //第二种put方法
        char[] chars = {'a', 'b', 'c'};
        buffer.put(chars);
        buffer.clear();
        //CharBuffer还可以使用String
        buffer.put("abc");
    }

    private static void testMark() {
        CharBuffer buffer = CharBuffer.allocate(10);
        showBuffer(buffer);
        //设置mark位置为3
        buffer.position(3).mark().position(5);
        showBuffer(buffer);
        //reset后，position=mark
        buffer.reset();
        showBuffer(buffer);
    }

    /**
     * 测试Buffer的各种属性
     */
    private static void testProperties() {
        CharBuffer buffer = CharBuffer.allocate(10);
        //buffer的初始状态
        showBuffer(buffer);
        //存入三个字符后的状态
        buffer.put("abc");
        showBuffer(buffer);
        //flip后的状态
        buffer.flip();
        showBuffer(buffer);
        //读取两个字符后的状态
        char c1= buffer.get();
        char c2=buffer.get();
        showBuffer(buffer);
        //clear后的状态
        buffer.clear();
        showBuffer(buffer);
    }

    /**
     * 显示buffer的position、limit、capacity和buffer中包含的字符，若字符为0，则替换为'.'
     * @param buffer
     */
    private static void showBuffer(CharBuffer buffer) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buffer.limit(); i++) {
            char c = buffer.get(i);
            if (c == 0) {
                c = '.';
            }
            sb.append(c);
        }
        System.out.printf("position=%d, limit=%d, capacity=%d, content=%s\n",
                buffer.position(),buffer.limit(),buffer.capacity(),sb.toString());
    }
}
