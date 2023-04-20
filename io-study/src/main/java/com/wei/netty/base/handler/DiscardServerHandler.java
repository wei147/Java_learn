package com.wei.netty.base.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: wei
 * @create: 2023-04-20 22:32
 **/
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    //有客户端连接进来就会触发这个方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接了");
    }

    //有读写事件发生就会触发这个方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // PooledUnsafeDirectByteBuf 把byte转为 string
        ByteBuf out = (ByteBuf) msg;
        System.out.println("msg is :  " + out.toString(CharsetUtil.UTF_8)); //解决中文乱码问题
    }
}
