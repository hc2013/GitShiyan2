package com.hc.io2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in=(ByteBuf)msg;
        System.out.println("Server received:"+in.toString(CharsetUtil.UTF_8));
        ctx.write(in);//将接受到的数据返回到client
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)  .addListener(ChannelFutureListener.CLOSE);
    }

    @Override  public void exceptionCaught(ChannelHandlerContext ctx,  Throwable cause) {  cause.printStackTrace();  ctx.close();  }
}
