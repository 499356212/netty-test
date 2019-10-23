package com.example.nettytest.socket.netty.messagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description NettyClientHandler
 * @Date 2019/9/27 13:51:29
 * @Author ljw
 */
public class NettyClientHandler extends ChannelHandlerAdapter {
    private final int sendNumber;

    private Person[] getPersons(){
        Person[] persons = new Person[sendNumber];
        Person person = null;
        for(int i = 0; i < sendNumber; i++){
            person = new Person();
            person.setName("name ==>> " + i);
            person.setAge(i);
            persons[i] = person;
        }
        return persons;
    }

    public NettyClientHandler(int sendNumber){
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Person[] persons = getPersons();
        for(Person person : persons){
            ctx.write(person);
        }
        ctx.flush();
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
