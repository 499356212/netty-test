package com.example.nettytest.test;

/**
 * @Description TODO
 * @Date 2019/9/3 10:12:38
 * @Author ljw
 */
public class Test {
    public static void main(String[] args){
        System.out.println(add(5,6));
        Math.sqrt(5);
    }

    public static int add(int a, int b){
        int _a = 0;
        int _b = 0;
        do {
            _a = a ^ b;
            _b = (a & b) << 1;
            a = _a;
            b = _b;
        } while (b != 0);
        return a;
    }
}
