package com.example.springbootproject.traceid;


import java.util.UUID;

/**
 * @author linzhou
 * @ClassName DefaultTraceIdContext.java
 * @createTime 2022年04月01日 15:05:00
 * @Description traceId上下文 链路跟踪
 */
public class DefaultTraceIdContext implements TraceIdContext{

    private static final TraceIdContext TRACE_ID_CONTEXT = new DefaultTraceIdContext();

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();


    @Override
    public String createTraceId() {
        String uuId = UUID.randomUUID().toString();
        threadLocal.set(uuId);
        return uuId;
    }

    @Override
    public String getTraceId() {
        return threadLocal.get();
    }

    @Override
    public String delTraceId() {
        String uuId = threadLocal.get();
        threadLocal.remove();
        return uuId;
    }


    public static String create(){
        return TRACE_ID_CONTEXT.createTraceId();
    }

    public static  String get(){
        return TRACE_ID_CONTEXT.getTraceId();
    }

    public static String del(){
        return TRACE_ID_CONTEXT.delTraceId();
    }
}
