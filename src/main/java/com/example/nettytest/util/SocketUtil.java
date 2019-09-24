package com.example.nettytest.util;

import java.io.Closeable;

/**
 * @Description SocketUtil
 * @Date 2019/9/19 09:36:12
 * @Author ljw
 */
public class SocketUtil {

    public static void close(Closeable s){
        try{
            if(s != null){
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
