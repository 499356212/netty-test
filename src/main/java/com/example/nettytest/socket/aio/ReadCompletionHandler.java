package com.example.nettytest.socket.aio;

import com.example.nettytest.util.DateUtil;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * ReadCompletionHandler
 *
 * @author ljw
 * @since 2019/9/24 17:20:03
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null) {
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("Server recive message : " + req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? DateUtil.now(DateUtil.yyyyMMddHHmmssSSS) : "BAD QUERY";
            doWrite(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String response) {
        if (response != null && response.trim().length() > 0) {
            System.out.println("Server answer message : " + response);
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    // 没法送完，继续发送
                    if (buffer.hasRemaining()) {
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
