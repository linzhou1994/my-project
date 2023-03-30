package com.example.springbootdubbpbiz.registry;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter;
import com.example.springbootdubbpbiz.config.DubboConfig;
import com.example.springbootdubbpbiz.util.SpringUtil;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-03-30 15:07
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class MyZookeeperRegistry extends ZookeeperRegistry {

    public MyZookeeperRegistry(URL url, ZookeeperTransporter zookeeperTransporter) {
        super(url, zookeeperTransporter);
    }

    @Override
    public void register(URL url) {
        DubboConfig config = SpringUtil.getBean(DubboConfig.class);
        // 获取自定义参数
        String customParam = config.getEvn();
        // 将自定义参数添加到服务提供者的URL中
        if (StringUtils.isNotEmpty(customParam)) {
            url = url.addParameter("customParam", customParam);
        }
        // 注册服务
        super.register(url);
    }

}
