package com.example.nettytest.socket.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * AcceptCompletionHandler
 *
 * @author ljw
 * @since 2019/9/24 16:59:33
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioSocketServerHandler>  {
    @Override
    public void completed(AsynchronousSocketChannel result, AioSocketServerHandler attachment) {
        // 客户端已经接入成功，继续去其他客户端
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AioSocketServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
