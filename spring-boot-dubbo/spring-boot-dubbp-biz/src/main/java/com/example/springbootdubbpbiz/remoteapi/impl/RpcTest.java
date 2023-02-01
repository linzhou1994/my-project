package com.example.springbootdubbpbiz.remoteapi.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.springbootdubboapi.remotepai.RemoteDubboTestService;
import org.springframework.stereotype.Component;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2022-06-25 17:54
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
@Component
public class RpcTest {
    @Reference
    private RemoteDubboTestService remoteDubboTestService;
}
