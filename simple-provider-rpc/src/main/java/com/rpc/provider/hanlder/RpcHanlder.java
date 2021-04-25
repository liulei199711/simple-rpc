package com.rpc.provider.hanlder;

import cn.hutool.core.util.ClassUtil;
import com.rpc.provider.commons.RpcInvokeInfo;
import com.rpc.provider.invoke.InvokeResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 服务rpc
 *
 * @author liulei
 **/
@ChannelHandler.Sharable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcHanlder extends ChannelInboundHandlerAdapter {


    private Map<String,Class<?>> classMap;




    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("channelRegistered----------------");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("ChannelHandlerContext -----------------------");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("------------------------>"+msg);
        if(msg instanceof RpcInvokeInfo){
            RpcInvokeInfo rpcInvokeInfo = (RpcInvokeInfo) msg;
            InvokeResult invokeResult  = new InvokeResult(classMap);
            Object invoke = invokeResult.invoke(rpcInvokeInfo);
            ctx.writeAndFlush(invoke);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete------------------------");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause);
        super.exceptionCaught(ctx, cause);
    }


}
