package com.example.nettytest.socket.netty.messagePack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * NettyClientHandler
 *
 * @author ljw
 * @since 2019/9/27 13:51:29
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
        System.out.println("Client revice message : " + msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
