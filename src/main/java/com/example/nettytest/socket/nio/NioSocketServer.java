package com.example.nettytest.socket.nio;

/**
 * NioSocketServer
 *
 * @author ljw
 * @since 2019/9/19 15:40:26
 */
public class NioSocketServer {

    public static void main(String[] args) {
        int port = 8000;
        NioSocketServerHandler server = new NioSocketServerHandler(port);
        new Thread(server, "NIO-MultiplexerSocketServer-001").start();
    }

}
