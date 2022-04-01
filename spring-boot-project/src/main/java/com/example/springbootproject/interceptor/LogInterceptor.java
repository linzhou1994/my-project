package com.example.springbootproject.interceptor;

import com.example.springbootproject.traceid.DefaultTraceIdContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author linzhou
 * @ClassName LogInterceptor.java
 * @createTime 2022年04月01日 15:20:00
 * @Description
 */
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = DefaultTraceIdContext.create();
        MDC.put("TRACE_ID", traceId);
        log.info("traceId:{}",traceId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
