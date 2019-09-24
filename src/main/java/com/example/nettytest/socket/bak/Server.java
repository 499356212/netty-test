package com.example.nettytest.socket.bak;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description TODO
 * @Date 2019/9/16 17:16:20
 * @Author ljw
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8000);
            System.out.println("启动服务器....");
            while(true){
                Socket s = ss.accept();
                System.out.println("客户端:"+s.getInetAddress().getLocalHost()+"已连接到服务器");

                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String msg;
                while(!(msg = br.readLine()).equals("exit")){
                    System.out.println("客户端：" + msg);
                }

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                bw.write("你好，客户端！"+"\n");
                bw.write("exit\n");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
