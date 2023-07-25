package com.sandro.proxy.config.v6_aop;

import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.proxy.config.AppV1Config;
import com.sandro.proxy.config.AppV2Config;
import com.sandro.proxy.config.v6_aop.aspect.LogTraceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AppV1Config.class, AppV2Config.class})
@Configuration
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace trace) {
        return new LogTraceAspect(trace);
    }

}
