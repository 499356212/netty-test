package com.example.nettytest.socket.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Description AioSocketClientHandler
 * @Date 2019/9/24 17:56:48
 * @Author ljw
 */
public class AioSocketClientHandler implements Runnable, CompletionHandler<Void, AioSocketClientHandler> {

    private String host;
    private int port;
    private CountDownLatch latch;
    private AsynchronousSocketChannel asynchronousSocketChannel;

    public AioSocketClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            asynchronousSocketChannel = AsynchronousSocketChannel.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        asynchronousSocketChannel.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
            asynchronousSocketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AioSocketClientHandler attachment) {
        String requestStr = "QUERY TIME ORDER";
        System.out.println("Client send message : " + requestStr);
        byte[] req = requestStr.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put(req);
        writeBuffer.flip();
        asynchronousSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    asynchronousSocketChannel.write(buffer, buffer, this);
                } else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    asynchronousSocketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer buffer) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            try {
                                String body = new String(bytes, "UTF-8");
                                System.out.println("Client recive message : " + body);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer buffer) {
                            try {
                                asynchronousSocketChannel.close();
                                latch.countDown();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buffer) {
                try {
                    asynchronousSocketChannel.close();
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AioSocketClientHandler attachment) {
        exc.printStackTrace();
        try {
            asynchronousSocketChannel.close();
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
