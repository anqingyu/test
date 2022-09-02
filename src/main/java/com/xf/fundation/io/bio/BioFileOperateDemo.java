package com.xf.fundation.io.bio;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * BIO操作文件的例子
 */
public class BioFileOperateDemo {
    public static void main(String[] args) throws IOException {
        // 递归列出所有目录
        print(new File("D:/test_temp"));

        // BIO读取文件（字节流,使用缓存）:常用于读二进制文件，如图片、声音、影像等文件
        copyFileByByBytesBuffered("D:/test_temp/a.txt", "D:/test_temp/b.txt");
        // BIO字符流，按行读取文件(字符缓存流，BufferedReader提供readLine方法读取一行文本)
        copyFileByCharacterBuffered("D:/test_temp/a.txt", "D:/test_temp/c.txt");

    }

    /**
     * BIO读取文件（字节流）:常用于读二进制文件，如图片、声音、影像等文件
     *   InputStream类中的三种read方法:
     *      read():返回值为0到255的int类型的值，返回值为字符的ASCII值(如a就返回97,n就返回110);
     *          如果没有可用的字节,因为已经到达流的末尾, -1返回的值
     *      read(byte[] b):返回值为实际读取的字节数;
     *          如果没有可用的字节,因为已经到达流的末尾, -1返回的值
     *      read( byte [] b , int off , int len):试图读取多达 len字节,但可能读取到少于len字节。返回实际读取的字节数为整数;
     *          如果没有可用的字节,因为已经到达流的末尾, -1返回的值
     */
    public static void copyFileByBytes(String readFileName, String writeFileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(readFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(writeFileName);

        //读取数据
        // 一次读取1024字节
        byte[] bytes = new byte[1024];
        int n;
        while((n = fileInputStream.read(bytes, 0, bytes.length)) != -1){
            //转换成字符串，这里可以实现字节到字符串的转换，比较实用
            String str = new String(bytes, 0, n, StandardCharsets.UTF_8);
            System.out.println(str);
            // 写入相关文件
            fileOutputStream.write(bytes, 0, n);
        }

        // 关流
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * BIO读取文件（字节流,使用缓存）:常用于读二进制文件，如图片、声音、影像等文件
     */
    public static void copyFileByByBytesBuffered(String readFileName, String writeFileName) throws IOException{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(readFileName));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFileName));

        // 先从输入流中读取2048字节到内存数组，再把内存数据写到输出流
        byte[] bytes = new byte[2048];
        int n;

        while((n = bis.read(bytes, 0, bytes.length)) != -1){
            String str = new String(bytes, 0, n, StandardCharsets.UTF_8);
            System.out.println(str);

            bos.write(bytes, 0, n);
        }

        // 清除缓存
        bos.flush();

        // 关闭流
        bis.close();
        bos.close();
    }

    /**
     * 以字符为单位读取文件(转换流)
     *    InputStreamReader和OutputStreamReader：都叫字符转换流，用于字节数据到字符数据之间的转换。可以指定字符的编码
     *    InputStreamReader输入流转换，即将从源输入的字节流转换成字符流，接收的是InputStream对象
     *    OutputStreamWriter输出转换流，功能是将输入的字符流转换成字节流。接收的对象是OutputStream。
     *
     * @param readFileName 读文件
     * @param writeFileName 写文件
     */
    public static void  copyFileByCharacter(String readFileName, String writeFileName) throws IOException{
        InputStreamReader in = new InputStreamReader(new FileInputStream(readFileName), "GBK");
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(writeFileName), "GBK");

        // 一次读取一个字符
        /*int len = -1;
        while ((len = in.read()) != -1){
            System.out.println(len);

            out.write(len);
        }*/

        // 一次读多个字符
        char[] tempChars = new char[30];
        int len;
        while ((len = in.read(tempChars)) != -1){
            System.out.println(new String(tempChars));
            out.write(tempChars, 0, len);
        }

        // 清除缓存
        out.flush();
        // 关闭流
        in.close();
        out.close();
    }

    /**
     * BIO字符流，按行读取文件(字符缓存流，BufferedReader提供readLine方法读取一行文本)
     * @param readFileName 读文件
     * @param writeFileName 写文件
     */
    public static void  copyFileByCharacterBuffered(String readFileName, String writeFileName) throws IOException{
        // FileInputStream可以设置编码格式，FileReader不可以
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(readFileName), StandardCharsets.UTF_8)); //这里主要是涉及中文
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(readFileName));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFileName), StandardCharsets.UTF_8));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFileName));

        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
            //写入相关文件
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }

        // 清除缓存
        bufferedWriter.flush();
        // 关闭流
        bufferedReader.close();
        bufferedWriter.close();
    }

    /**
     * BIO:使用Reader、PrintWriter读取文件(字节流)
     */
    public static void copyFileByPrintWriter(String readFileName, String writeFileName) throws IOException{
        //读取文件(字节流)
        Reader in = new InputStreamReader(new FileInputStream(readFileName),"GBK");
        //写入相应的文件
//        PrintWriter out = new PrintWriter(new FileWriter(writeFileName));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(writeFileName), StandardCharsets.UTF_8));

        int len;
        while ((len = in.read()) != -1) {
            System.out.println(len);
            //写入相关文件
            out.write(len);
        }

        // 清除缓存
        out.flush();
        // 关闭流
        in.close();
        out.close();
    }

    /**
     * BIO:使用 RandomAccessFile 读取文件
     *    RandomAccessFile支持"随机访问"的方式：①RandomAccessFile可以自由访问文件的任意位置。②RandomAccessFile允许自由定位文件记录指针。③RandomAccessFile只能读写文件而不是流。
     *
     * @param file
     * @param newFile
     */
    public static void randomAccessRead(File file , File newFile){
        long d1 = System.currentTimeMillis();
        RandomAccessFile raf = null;
        OutputStream output = null;
        try {
            raf = new RandomAccessFile(file , "rw");
            output = new FileOutputStream(newFile);
            // 每次读取内容长度
            int len = 0;
            // 内容缓冲区
            byte[] data = new byte[1024];
            while((len = raf.read(data)) != -1){
                output.write(data, 0, len);
            }
            long d2 = System.currentTimeMillis();
            System.out.println("randomAccessRead读取完成，耗时：" + (d2 - d1));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(raf != null){
                    raf.close();
                }
                if(output != null){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 列出指定目录的全部内容:递归调用
     * @param file
     */
    public static void print(File file){
        // 判断对象是否为空
        if(file!=null){
            // 如果是目录
            if(file.isDirectory()){
                // 列出全部的文件
                File[] f = file.listFiles() ;
                // 判断此目录能否列出
                if(f!=null){
                    for (File value : f) {
                        // 因为给的路径有可能是目录，所以，继续判断，继续递归。
                        print(value);
                    }
                }
            }else{
                // 输出路径
                System.out.println(file) ;
            }
        }
    }
}
