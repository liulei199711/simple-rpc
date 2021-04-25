package com.rpc.provider.invoke;

import cn.hutool.core.util.ClassUtil;
import com.rpc.provider.commons.RpcInvokeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author liulei
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvokeResult {

    private Map<String,Class<?>> classMap;




    public Object invoke(RpcInvokeInfo rpcInvokeInfo){
        Class<?> beanClass = rpcInvokeInfo.getBeanClass();
        Method method = rpcInvokeInfo.getMethod();
        Object[] args = rpcInvokeInfo.getArgs();
        String className = ClassUtil.getClassName(beanClass, false);
        Class<?> classImpl = classMap.get(className);
        try {
            // TODO 过滤Object方法
            return method.invoke(classImpl.newInstance(),args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
