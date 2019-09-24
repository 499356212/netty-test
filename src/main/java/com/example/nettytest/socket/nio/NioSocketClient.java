package com.example.nettytest.socket.nio;

/**
 * @Description TODO
 * @Date 2019/9/23 09:03:27
 * @Author ljw
 */
public class NioSocketClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8000;
        new Thread(new SocketClientHandle(host, port), "NIO-SocketClient-001").start();
    }
}
