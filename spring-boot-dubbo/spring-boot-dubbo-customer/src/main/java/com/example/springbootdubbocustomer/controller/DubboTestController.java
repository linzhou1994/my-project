package com.example.springbootdubbocustomer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.springbootdubboapi.remotepai.RemoteDubboTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author linzhou
 * @ClassName DubboTestController.java
 * @createTime 2022年03月13日 13:01:00
 * @Description
 */
@RestController
@RequestMapping("dubbo")
@Slf4j
public class DubboTestController {
    @Reference
    private RemoteDubboTestService remoteDubboTestService;

    @RequestMapping("test")
    public String dubboTest(String str){
        String dubboTest = remoteDubboTestService.dubboTest(str);
        log.info("DubboTestController:{}",dubboTest);

        return dubboTest;
    }
}
