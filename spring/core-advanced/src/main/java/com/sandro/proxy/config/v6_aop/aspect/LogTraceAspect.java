package com.sandro.proxy.config.v6_aop.aspect;

import com.sandro.part3.trace.TraceStatus;
import com.sandro.part3.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@RequiredArgsConstructor
@Aspect
public class LogTraceAspect {

    private final LogTrace trace;

    @Around("execution(* com.sandro.proxy.app..*(..)) && !execution(* com.sandro.proxy.app..noLog(..))") // 포인트컷
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // 어드바이스
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = trace.begin(message);

            Object result = joinPoint.proceed();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

}
