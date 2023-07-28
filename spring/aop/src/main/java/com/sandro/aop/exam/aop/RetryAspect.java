package com.sandro.aop.exam.aop;

import com.sandro.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();

        Exception exceptionHolder = null;

        for (int retryCount = 0; retryCount <= maxRetry ; retryCount++) {
            if (retryCount > 0)
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                log.warn("Exception 발생 및 재시도 {} args={}", joinPoint.getSignature(), joinPoint.getArgs());
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
