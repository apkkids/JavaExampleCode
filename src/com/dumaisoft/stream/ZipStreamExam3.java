package com.dumaisoft.stream;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by wxb on 2016/8/19.
 */
public class ZipStreamExam3 {
    public static void main(String[] args) {
        try {
            File srcFile = new File("d:\\zipmultidir.zip");
            System.out.println(srcFile.getCanonicalPath());
            String curDir = srcFile.getParent()+File.separator+"destDir"+File.separator;

            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(srcFile)));
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            int len = 0;
            while ((ze = zipInputStream.getNextEntry()) != null) {
                String filePath = curDir + ze.getName();
                File destFile = new File(filePath);
                File destDir = new File(destFile.getParent());
                if(!destDir.exists()){
                    destDir.mkdirs();
                }
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                while ((len = zipInputStream.read(buf)) != -1) {
                    bufferedOutputStream.write(buf, 0, len);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
