package com.example.nettytest.socket.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * SocketClient
 *
 * @author ljw
 * @since 2019/9/16 14:38:31
 */
public class SocketClient{

    public static void main(String[] args) {
        for(int i = 1; i <= 3000; i ++){
            doStart(String.valueOf(i));
        }
    }

    private static synchronized void doStart(String threadName){
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1",8000);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //向服务器端发送消息
                bw.write("你好，服务端，我是：" + threadName + "\n");
                bw.write("exit\n");
                bw.flush();

                //读取服务器返回的消息
                String msg;
                while(!(msg = br.readLine()).equals("exit")){
                    System.out.println("服务端：" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, threadName).start();
    }
}
