package com.example.springbootdubbpbiz.remoteapi.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.springbootdubboapi.remotepai.RemoteDubboTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author linzhou
 * @ClassName RemoteDubboTestServiceImpl.java
 * @createTime 2022年03月13日 12:49:00
 * @Description
 */
@Slf4j
@Service
@Component
public class RemoteDubboTestServiceImpl implements RemoteDubboTestService {
    @Override
    public String dubboTest(String str) {
        log.info("dubboTest:{}", str);
        return "dubboTest:" + str;
    }
}
