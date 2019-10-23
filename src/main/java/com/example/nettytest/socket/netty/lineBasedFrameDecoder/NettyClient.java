package com.example.nettytest.socket.netty.lineBasedFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Description NettyClient
 * @Date 2019/9/27 11:38:02
 * @Author ljw
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8000;
        new NettyClient().connect(host, port);
    }

    public static void connect(String host, int port) throws Exception {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new StringDecoder())
                                    .addLast(new NettyClientHandler());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture future = bootstrap.connect(host, port).sync();
            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }

}
