package com.example.nettytest.socket.bio;

import com.example.nettytest.util.SocketUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 服务端Socketc处理线程
 *
 * @author ljw
 * @since 2019/9/18 17:17:28
 */
public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 处理客户端传来的消息
            String msg;
            while (!(msg = br.readLine()).equals("exit")) {
                System.out.println("客户端：" + msg);
            }

            // 给客户端返回消息
            bw.write("你好，客户端！\n");
            bw.write("exit\n");
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SocketUtil.close(br);
            SocketUtil.close(bw);
            SocketUtil.close(socket);
        }

    }

}
