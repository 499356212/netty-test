package com.example.nettytest.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;

/**
 * @Description NettyServer
 * @Date 2019/9/2 09:48:41
 * @Author ljw
 */
public class NettyServer {

    public static void main(String[] args) {

        NioEventLoopGroup boos = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boos, worker)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childAttr(AttributeKey.valueOf("childAttr"), "childAttrValue")
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(new StringDecoder());
                            nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                    System.out.println(s);
                                }
                            });
                        }
                    })
                    .bind(8000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
