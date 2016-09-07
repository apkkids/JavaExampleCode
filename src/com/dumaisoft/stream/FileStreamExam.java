package com.dumaisoft.stream;

import java.io.*;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2016/9/5
 * Create Time: 20:31
 * Description: File Stream example code.
 */

public class FileStreamExam {

    public static void main(String[] args) {
//        exeWriteString2File();
//        exeReadFile();
//        exeWriteString2FileWithEncoding();
//        fileOutputStreamExam();
//        fileInputStreamExam();
//        fileReaderExam();
//        fileWriterExam();
        inputStreamReaderExam();
//        outputStreamWriterExam();
    }


    private static void writeString2File(String info, String filename) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write(info);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exeWriteString2File() {
        String info = "Today is a good day.\r\n今天天气很好。";
        try {
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output1.txt";
            writeString2File(info, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String filename) {
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            StringBuilder sb = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str + "\n");
            }
            str = sb.toString();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static void exeReadFile() {
        try {
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output1.txt";
            String str = readFile(outputfile);
            System.out.println(str);

            outputfile = new File(".").getCanonicalPath() + File.separator + "output_gbk.txt";
            str = readFile(outputfile);
            System.out.println(str);

            outputfile = new File(".").getCanonicalPath() + File.separator + "output_utf-8.txt";
            str = readFile(outputfile);
            System.out.println(str);

            outputfile = new File(".").getCanonicalPath() + File.separator + "output_utf-16.txt";
            str = readFile(outputfile);
            System.out.println(str);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeString2FileWithEncoding(String info, String filename, String charset) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filename));
            byte[] bytes = info.getBytes(charset);
            bos.write(bytes);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exeWriteString2FileWithEncoding() {
        try {
            String info = "Today is a good day.\r\n今天天气很好。";
            String output_gbk = new File(".").getCanonicalPath() + File.separator + "output_gbk.txt";
            writeString2FileWithEncoding(info, output_gbk, "gbk");

            String output_utf8 = new File(".").getCanonicalPath() + File.separator + "output_utf-8.txt";
            writeString2FileWithEncoding(info, output_utf8, "utf-8");

            String output_utf16 = new File(".").getCanonicalPath() + File.separator + "output_utf-16.txt";
            writeString2FileWithEncoding(info, output_utf16, "utf-16");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileOutputStreamExam() {
        try {
            //得到当前目录下名为output.txt文件的路径
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output.txt";
            System.out.println(outputfile);
            FileOutputStream fos = new FileOutputStream(outputfile);
            String str = "Today is a good day.\r\n今天天气很好。";
            byte[] buf = str.getBytes("GBK");
            fos.write(buf, 0, buf.length);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileInputStreamExam() {
        try {
            //得到当前目录下名为output.txt文件的路径
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output.txt";
            FileInputStream fis = new FileInputStream(outputfile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            String s = baos.toString("GBK");
            System.out.println(s);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void outputStreamWriterExam() {
        try {
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output3.txt";
            //此处可以指定编码
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outputfile), "GBK");

            String str = "Today is a good day.\r\n今天天气很好。";
            outputStreamWriter.write(str);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inputStreamReaderExam() {
        try {
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output3.txt";
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(outputfile), "GBK");
            CharArrayWriter charArrayWriter = new CharArrayWriter();
            char[] buf = new char[1024];
            int len = 0;
            while((len=inputStreamReader.read(buf))!=-1){
                charArrayWriter.write(buf,0,len);
            }
            String s = charArrayWriter.toString();
            System.out.println(s);
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileWriterExam() {
        try {
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output2.txt";
            FileWriter fileWriter = new FileWriter(outputfile);
            String str = "今天天气很好abc.";
            fileWriter.write(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileReaderExam() {
        try {
            String outputfile = new File(".").getCanonicalPath() + File.separator + "output2.txt";
            FileReader fileReader = new FileReader(outputfile);
            char[] buf = new char[1024];
            int len = fileReader.read(buf);
            String s = new String(buf, 0, len);
            System.out.println(s);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String byteToHexString(byte b) {
        String s = Integer.toHexString(b);
        int len = s.length();
        if (len >= 2) {
            s = s.substring(len - 2);
        } else {
            s = "0" + s;
        }
        return s;
    }

}
