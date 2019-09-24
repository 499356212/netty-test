package com.example.nettytest.socket.nio;

/**
 * @Description NioSocketServer
 * @Date 2019/9/19 15:40:26
 * @Author ljw
 */
public class NioSocketServer {

    public static void main(String[] args) {
        int port = 8000;
        MultiplexerSocketServer server = new MultiplexerSocketServer(port);
        new Thread(server, "NIO-MultiplexerSocketServer-001").start();
    }

}
