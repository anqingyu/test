package com.xf.fundation.io.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Java NIO中的DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包。
 */
public class DatagramChannelDemo {
    //发送的实现
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
        //打开 DatagramChannel
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1",9999);

        //发送
        while(true) {
            ByteBuffer buffer = ByteBuffer.wrap("过去、现在、将来：欣喜于彩虹，也无惧于风雨".getBytes(StandardCharsets.UTF_8));
            sendChannel.send(buffer,sendAddress);
            System.out.println("已经发送完成");
            Thread.sleep(1000);
        }
    }

    //接收的实现
    @Test
    public void receiveDatagram() throws IOException {
        //打开 DatagramChannel
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress(9999);
        //绑定
        receiveChannel.bind(sendAddress);

        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //接收
        while(true) {
            buffer.clear();
            SocketAddress socketAddress = receiveChannel.receive(buffer);
            buffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(Charset.forName("UTF-8").decode(buffer));
        }
    }

}
