package com.example.springbootproject.traceid;

/**
 * @author linzhou
 * @ClassName TraceIdContext.java
 * @createTime 2022年04月01日 15:04:00
 * @Description
 */
public interface TraceIdContext {

    String createTraceId();

    String getTraceId();

    String delTraceId();
}
