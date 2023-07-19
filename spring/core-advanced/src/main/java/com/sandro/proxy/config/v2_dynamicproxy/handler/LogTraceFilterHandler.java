package com.sandro.proxy.config.v2_dynamicproxy.handler;

import com.sandro.part3.trace.TraceStatus;
import com.sandro.part3.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler {

    private final LogTrace trace;
    private final String[] patterns;
    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 메서드 이름 필터
        String methodName = method.getName();
        if (isInvalid(methodName))
            return method.invoke(target, args);

        TraceStatus status = null;
        try {
            String message = method.getDeclaringClass().getSimpleName() + "." + methodName + "()";
            status = trace.begin(message);

            Object result = method.invoke(target, args);

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private boolean isInvalid(String methodName) {
        return !PatternMatchUtils.simpleMatch(patterns, methodName);
    }

    @RequiredArgsConstructor
    public static class LogTraceFilterHandlerFactory {
        private final LogTrace trace;
        private final String[] patterns;

        public LogTraceFilterHandler getInstance(Object target) {
            return new LogTraceFilterHandler(trace, patterns, target);
        }
    }
}
