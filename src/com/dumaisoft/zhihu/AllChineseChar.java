package com.dumaisoft.zhihu;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by wxb on 2017/11/22 0022.
 */
public class AllChineseChar {
    public static void main(String[] args) throws UnsupportedEncodingException {
        StringBuilder gbk2 = new StringBuilder();
        byte[] bytes = new byte[2];
        for (int i = 0xb0; i <= 0xf7; i++) {
            for (int j = 0xa1; j <= 0xfe; j++) {
                bytes[0] = (byte) (i & 0xFF);
                bytes[1] = (byte) (j & 0xFF);
                String str = new String(bytes, "GBK");
                gbk2.append(str);
            }
        }
        System.out.println("GBK/2 编码段汉字数量：" + gbk2.length());
        System.out.println(gbk2.toString());

        StringBuilder gbk3 = new StringBuilder();
        for (int i = 0x81; i <= 0xa0; i++) {
            for (int j = 0x40; j <= 0xfe; j++) {
                if (j != 0x7f) {
                    bytes[0] = (byte) (i & 0xFF);
                    bytes[1] = (byte) (j & 0xFF);
                    String str = new String(bytes, "GBK");
                    gbk3.append(str);
                }
            }
        }
        System.out.println("GBK/3 编码段汉字数量：" + gbk3.length());
        System.out.println(gbk3.toString());

        StringBuilder gbk4 = new StringBuilder();
        for (int i = 0xaa; i <= 0xfe; i++) {
            for (int j = 0x40; j <= 0xa0; j++) {
                if (j != 0x7f) {
                    bytes[0] = (byte) (i & 0xFF);
                    bytes[1] = (byte) (j & 0xFF);
                    String str = new String(bytes, "GBK");
                    gbk4.append(str);
                }
            }
        }
        System.out.println("GBK/4 编码段汉字数量：" + gbk4.length());
        System.out.println(gbk4.toString());
        System.out.println("全部汉字数量为：" + (gbk2.length() + gbk3.length() + gbk4.length()));
    }
}
