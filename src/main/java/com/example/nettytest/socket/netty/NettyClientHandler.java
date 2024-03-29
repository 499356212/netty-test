package com.example.nettytest.socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description NettyClientHandler
 * @Date 2019/9/27 13:51:29
 * @Author ljw
 */
public class NettyClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String requesetStr = "QUERY TIME ORDER";
        System.out.println("Client send message : " + requesetStr);
        byte[] req = requesetStr.getBytes();
        ByteBuf byteBuf = Unpooled.buffer(req.length);
        byteBuf.writeBytes(req);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Client revice message : " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
