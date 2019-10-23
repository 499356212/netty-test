package com.example.nettytest.socket.netty.delimiterBasedFrameDecoder;

import com.example.nettytest.util.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * NettyServerHandler
 *
 * @author ljw
 * @since 2019/9/27 11:17:03
 */
public class NettyServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Server recive message : " + body + " ; the counter is : " + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? DateUtil.now(DateUtil.yyyyMMddHHmmssSSS) : "BAD QUERY";
        currentTime += "$_";
        System.out.println("Server answer message : " + currentTime);
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
