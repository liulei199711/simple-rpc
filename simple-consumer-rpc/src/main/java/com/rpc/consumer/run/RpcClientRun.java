package com.rpc.consumer.run;

import com.rpc.api.DemoService;
import com.rpc.consumer.hanlder.RpcClientHanlder;
import com.rpc.consumer.proxy.RpcProxy;
import com.rpc.provider.commons.RpcInvokeInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Proxy;

/**
 * @author liulei
 **/
public class RpcClientRun {



    public static void main(String[] args) {
        DemoService demoService = (DemoService)Proxy.newProxyInstance(DemoService.class.getClassLoader(), new Class[]{DemoService.class}, new RpcProxy());
        String hello = demoService.sayHello("hello");
        System.out.println(hello);
    }



    public Object start(RpcInvokeInfo rpcInvokeInfo) {
        return run(rpcInvokeInfo);
    }

    public Object run(RpcInvokeInfo rpcInvokeInfo)  {
        RpcClientHanlder rpcClientHanlder = new RpcClientHanlder();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(rpcClientHanlder);
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8889).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(rpcInvokeInfo);
            // 当channel被关闭的时候会通知此处关闭chanel（closeFuture方法）
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
        return rpcClientHanlder.getResult();
    }


}
