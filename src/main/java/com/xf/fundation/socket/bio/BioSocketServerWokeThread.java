package com.xf.fundation.socket.bio;

import java.io.*;
import java.net.Socket;

/**
 * 服务端的任务处理类
 */
public class BioSocketServerWokeThread implements Runnable {
    Socket so = null;

    public BioSocketServerWokeThread(Socket so) {
        this.so = so;
    }

    /**
     *  线程执行的操作，响应客户端的请求
     */
    public void run() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        PrintWriter printWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            // 3.获取一个输入流，并读取客户端信息
            inputStream = so.getInputStream();
            // 将字节输入流包装成字符输入流
            inputStreamReader = new InputStreamReader(inputStream);
            // 加上缓冲流，提高效率
            bufferedReader = new BufferedReader(inputStreamReader);
            String info = null;
            while ((info = bufferedReader.readLine()) != null){
                // 循环读取客户端信息
                System.out.println("我是服务器，客户端说：" + info);
            }
            // 关闭输入流
            so.shutdownInput();
            // 4.获取一个输出流，向客户端输出信息,响应客户端的请求
            // 字节输出流
            outputStream = so.getOutputStream();
            // 字符输出流
            printWriter = new PrintWriter(outputStream);
            // 缓冲输出流
            bufferedWriter = new BufferedWriter(printWriter);
            bufferedWriter.write("欢迎您！");
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                // 5.关闭资源
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
                if(printWriter != null){
                    printWriter.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(inputStreamReader != null){
                    inputStream.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
