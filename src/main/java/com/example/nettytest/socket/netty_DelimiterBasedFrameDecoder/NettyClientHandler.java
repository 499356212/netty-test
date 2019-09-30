package com.example.nettytest.socket.netty_DelimiterBasedFrameDecoder;

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

    private int counter;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String requesetStr = "QUERY TIME ORDER" + "$_";
        byte[] req = requesetStr.getBytes();
        for(int i = 0; i < 100; i++){
            System.out.println("Client send message : " + requesetStr);
            ByteBuf byteBuf = Unpooled.buffer(req.length);
            byteBuf.writeBytes(req);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Client revice message : " + body + " ; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
