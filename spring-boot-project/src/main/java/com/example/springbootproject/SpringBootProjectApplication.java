package com.example.springbootproject;

//import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.springbootproject.mapper")//dao层目录
@EnableTransactionManagement
@EnableAspectJAutoProxy
//@EnableApolloConfig
public class SpringBootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProjectApplication.class, args);
    }

}
