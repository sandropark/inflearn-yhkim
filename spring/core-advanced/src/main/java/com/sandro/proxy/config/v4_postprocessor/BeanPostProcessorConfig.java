package com.sandro.proxy.config.v4_postprocessor;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.config.AppV1Config;
import com.sandro.proxy.config.AppV2Config;
import com.sandro.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.sandro.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({AppV1Config.class, AppV2Config.class})
@Configuration
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace trace) {
        return new PackageLogTracePostProcessor("com.sandro.proxy.app", getAdvisor(trace));
    }

    private DefaultPointcutAdvisor getAdvisor(LogTrace trace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }

}
