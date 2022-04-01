package com.example.springbootdubbocustomer.filter;


import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author linzhou
 * @ClassName DubboTraceInfoAttachmentFilter.java
 * @createTime 2022年04月01日 16:02:00
 * @Description 服务消费方：附加trace_id的过滤器
 */

@Activate(group = {"consumer"})
@Slf4j
public class DubboTraceInfoAttachmentFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = UUID.randomUUID().toString();
        log.info("Attachment traceId = {}", traceId);

        RpcContext.getContext().setAttachment("traceId", traceId);
        return invoker.invoke(invocation);
    }
}
