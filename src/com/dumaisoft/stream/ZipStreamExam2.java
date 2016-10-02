package com.dumaisoft.stream;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipStreamExam2 {
    public static void main(String[] args) {
        try {
            File file = new File("d:\\zipmultidir");
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream("d:\\zipmultidir.zip")));
            zipDir(file, zos, file);
            zos.flush();
            zos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //压缩一个目录至zip文件
    private static void zipDir(File dir, ZipOutputStream zos, File rootDir) throws IOException {
        if (!dir.isDirectory())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                System.out.println(files[i].getAbsolutePath());
                String now = files[i].getAbsolutePath();
                String root = rootDir.getAbsolutePath();
                String name = now.substring(root.length() + 1);
                System.out.println(name);

                FileInputStream fis = new FileInputStream(files[i]);

                byte buf[] = new byte[1024];
                int len = 0;
                ZipEntry ze = new ZipEntry(name);
                zos.putNextEntry(ze);
                while ((len = fis.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                fis.close();
            } else if (files[i].isDirectory()) {
                zipDir(files[i], zos, rootDir);
            }
        }
    }
}
