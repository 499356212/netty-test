package com.example.nettytest.socket.aio;

/**
 * @Description TODO
 * @Date 2019/9/24 17:55:25
 * @Author ljw
 */
public class AioSocketClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8000;
        Long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            new Thread(new AioSocketClientHandler(host, port), "AIO-SocketClient-001").start();
        }
    }
}
