package com.example.nettytest.socket.aio;

/**
 * @Description AioSocketServer
 * @Date 2019/9/24 15:57:15
 * @Author ljw
 */
public class AioSocketServer {

    public static void main(String[] args){
        int port = 8000;
        AioSocketServerHandler aioSocketServerHandler = new AioSocketServerHandler(port);
        new Thread(aioSocketServerHandler, "AIO-SocketServer-001").start();
    }
}
