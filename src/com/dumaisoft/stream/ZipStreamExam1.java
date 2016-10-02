package com.dumaisoft.stream;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by wxb on 2016/8/19.
 */
public class ZipStreamExam1 {
    public static void main(String[] args) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("D:\\input.txt"));
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream("d:\\output.zip")));
            byte[] buf = new byte[1024];
            int len = 0;
            ZipEntry ze = new ZipEntry("input.txt");
            zipOutputStream.putNextEntry(ze);
            while ((len = bufferedInputStream.read(buf)) != -1) {
                zipOutputStream.write(buf, 0, len);
            }
            zipOutputStream.flush();
            zipOutputStream.close();
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
