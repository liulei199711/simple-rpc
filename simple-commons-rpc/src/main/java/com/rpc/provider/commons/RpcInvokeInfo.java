package com.rpc.provider.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author liulei
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcInvokeInfo implements Serializable {

    private Class<?> beanClass;

    private Method method;

    private Object[] args;

    private Class[] argsType;

}
