package com.dumaisoft.zhihu;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/16
 * Create Time: 19:26
 * Description:
 */
public class TupleExam {
    //二元泛型元组类
    private static class TwoTuple<A, B> {
        public final A first;
        public final B second;
        public TwoTuple(A a, B b) {
            first = a;
            second = b;
        }
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    //你的方法，修改返回值为一个元组
    public TwoTuple<ByteBuffer,BufferedImage> getImageData(ByteBuffer buf, BufferedImage image) {
        TwoTuple<ByteBuffer, BufferedImage> tuple = new TwoTuple<>(buf, image);
        return tuple;
    }
}
