package com.example.nettytest.util;

import java.io.Closeable;

/**
 * SocketUtil
 *
 * @author ljw
 * @since 2019/9/19 09:36:12
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
