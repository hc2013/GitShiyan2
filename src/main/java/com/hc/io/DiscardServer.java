package com.hc.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
    private int port;
    public DiscardServer(int port){
        this.port=port;
    }

    public void run() throws Exception{
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        System.out.println("准备运行端口："+port);

        try{
            ServerBootstrap bootstrap=new ServerBootstrap();

            bootstrap=bootstrap.group(bossGroup,workerGroup);
            bootstrap=bootstrap.channel(NioServerSocketChannel.class);

            bootstrap=bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new DiscardServerHandler());
                }
            });

            bootstrap=bootstrap.option(ChannelOption.SO_BACKLOG,128);
            bootstrap=bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f=bootstrap.bind(port).sync();
            System.out.println("1111");
            f.channel().closeFuture().sync();
        }finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
    public static void main(String args[]) throws Exception{
        new DiscardServer(8080).run();

        System.out.println("server run");
    }
}
