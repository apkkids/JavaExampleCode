package com.dumaisoft.stream;

import java.io.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/9/16
 * Create Time: 20:05
 * Description:
 */
public class BufferedStreamExam {
    public static void main(String[] args) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("d:\\input.txt"));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("d:\\output.txt"));
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buf)) != -1) {
                bufferedOutputStream.write(buf, 0, len);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
