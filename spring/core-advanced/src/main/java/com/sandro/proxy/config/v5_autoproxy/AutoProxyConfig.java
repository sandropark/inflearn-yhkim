package com.sandro.proxy.config.v5_autoproxy;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.config.AppV1Config;
import com.sandro.proxy.config.AppV2Config;
import com.sandro.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AppV1Config.class, AppV2Config.class})
@Configuration
public class AutoProxyConfig {

//    @Bean
    public Advisor advisor1(LogTrace trace) {
        return new DefaultPointcutAdvisor(getPointcut(), new LogTraceAdvice(trace));
    }

    @Bean
    Advisor advisor2(LogTrace trace) {
        return new DefaultPointcutAdvisor(getAspectJPointcut(), new LogTraceAdvice(trace));
    }

    private Pointcut getAspectJPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution(* com.sandro.proxy.app..*(..))");   // app 하위의 모든 클래스를 대상
        pointcut.setExpression(
                "execution(* com.sandro.proxy.app..*(..)) && !execution(* com.sandro.proxy.app..noLog(..))"
        );    // app 하위의 모든 클래스를 대상, noLog 메서드는 제외
        return pointcut;
    }

    private Pointcut getPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        return pointcut;
    }

}
