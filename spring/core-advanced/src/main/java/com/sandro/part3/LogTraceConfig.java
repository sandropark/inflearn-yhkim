package com.sandro.part3;

import com.sandro.part3.trace.callback.TraceTemplate;
import com.sandro.part3.trace.logtrace.LogTrace;
import com.sandro.part3.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {
    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    @Bean
    public TraceTemplate traceTemplate(LogTrace trace) {
        return new TraceTemplate(trace);
    }
}
