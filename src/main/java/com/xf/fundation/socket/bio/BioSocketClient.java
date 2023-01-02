package com.xf.fundation.socket.bio;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

public class BioSocketClient {
    @Test
    public void test002() throws IOException {
        //1.创建客户端Socket，指定服务器地址和端口
        Socket so=new Socket("localhost", 8888);//端口号要和服务器端相同
        //2.获取输出流，向服务器端发送登录的信息
        OutputStream os=so.getOutputStream();//字节输出流
        PrintWriter pw=new PrintWriter(os);//字符输出流
        BufferedWriter bw=new BufferedWriter(pw);//加上缓冲流
        bw.write("用户名：admin;密码：123");
        bw.flush();
        so.shutdownOutput();//关闭输出流
        //3.获取输入流，得到服务端的响应信息
        InputStream is=so.getInputStream();
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader br=new BufferedReader(isr);
        String info=null;
        while((info=br.readLine())!=null){
            System.out.println("我是客户端，服务器说:"+info);
        }

        //4.关闭资源
        bw.close();
        pw.close();
        os.close();
        so.close();
    }

    @Test
    public void test001() throws IOException {
        // 1.创建客户端socket，指定服务器端地址和端口
        Socket clientSocket = new Socket("localhost", 8888);
        // 2.获取输出流，向服务器端发送信息
        OutputStream outputStream = clientSocket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
        bufferedWriter.write("你好！-------from client");
        bufferedWriter.flush();
        clientSocket.shutdownOutput();
        // 3.关闭资源
        bufferedWriter.close();
        printWriter.close();
        outputStream.close();
        clientSocket.close();

    }
}
