package com.rpc.consumer.proxy;

import com.rpc.consumer.run.RpcClientRun;
import com.rpc.provider.commons.RpcInvokeInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liulei
 **/
public class RpcProxy implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        // 調用远程服务
        RpcInvokeInfo rpcInvokeInfo = new RpcInvokeInfo();
        rpcInvokeInfo.setBeanClass(proxy.getClass());
        rpcInvokeInfo.setMethod(method);
        rpcInvokeInfo.setArgs(args);
        rpcInvokeInfo.setArgsType(method.getParameterTypes());
        return rpcInvoke(rpcInvokeInfo);
    }

    public Object rpcInvoke(RpcInvokeInfo rpcInvokeInfo) {
        RpcClientRun rpcClientRun = new RpcClientRun();
        return rpcClientRun.start(rpcInvokeInfo);
    }
}
