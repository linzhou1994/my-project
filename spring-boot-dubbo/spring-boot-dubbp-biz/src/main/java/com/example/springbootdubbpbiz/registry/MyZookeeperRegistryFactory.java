package com.example.springbootdubbpbiz.registry;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.springframework.stereotype.Component;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-03-30 15:59
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
@Component
public class MyZookeeperRegistryFactory  extends AbstractRegistryFactory {

    private ZookeeperTransporter zookeeperTransporter;

    public MyZookeeperRegistryFactory() {
        int i=0;
    }

    public void setZookeeperTransporter(ZookeeperTransporter zookeeperTransporter) {
        this.zookeeperTransporter = zookeeperTransporter;
    }

    public Registry createRegistry(URL url) {
        return new MyZookeeperRegistry(url, zookeeperTransporter);
    }

}
