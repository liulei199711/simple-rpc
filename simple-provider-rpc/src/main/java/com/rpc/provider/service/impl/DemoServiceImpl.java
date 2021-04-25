package com.rpc.provider.service.impl;

import com.rpc.api.DemoService;

import java.awt.*;

/**
 * @author liulei
 **/
public class
    DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "收到"+ name;
    }
}
