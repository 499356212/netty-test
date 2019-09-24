package com.example.nettytest.socket.bio;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description SocketServer
 * @Date 2019/9/16 13:59:28
 * @Author ljw
 */
public class SocketServer{

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(0, 1000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    public static void main(String[] args) {
        doStart();
    }

    private static void doStart(){
        new Thread(() -> {
            try{
                ServerSocket serverSocket = new ServerSocket(8000);
                System.out.println("服务端启动......");
                Socket socket;
                while(true) {
                    // 阻塞接收客户端的请求
                    socket = serverSocket.accept();
                    System.out.println("接收到:" + socket.getInetAddress() + "的请求");
                    // 同步阻塞IO
//                    new Thread(new ClientHandler(socket)).start();
                    // 伪异步IO
                    threadPool.submit(new ClientHandler(socket));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
