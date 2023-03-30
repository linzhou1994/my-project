package com.example.springbootdubbpbiz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-03-30 15:08
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "dubbo")
public class DubboConfig {
    private String evn;
}
