package com.rpc.provider.run;


import com.rpc.provider.hanlder.RpcHanlder;
import com.rpc.provider.service.impl.DemoServiceImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei
 **/
public class RpcServerHanlderRun {

    public static final int port = 8889;

    public static void main(String[] args) {
        RpcServerHanlderRun rpcServerHanlderRun = new RpcServerHanlderRun();
        Map<String,Class<?>> classMap = new HashMap<>();
        classMap.put("com.rpc.api.DemoService", DemoServiceImpl.class);
        RpcHanlder rpcHanlder = new RpcHanlder(classMap);
        rpcServerHanlderRun.start(rpcHanlder);
    }

    public void start(RpcHanlder rpcHanlder) {
        // 启动服务
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(rpcHanlder);
                    }
                });
        // 绑定端口
        try {
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭
            eventLoopGroup.shutdownGracefully();
        }

    }

}
